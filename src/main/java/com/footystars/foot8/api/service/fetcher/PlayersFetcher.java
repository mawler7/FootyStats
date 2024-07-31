package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.players.PlayersApiResponse;
import com.footystars.foot8.api.model.players.player.PlayerApi;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.player.PlayerInfoService;
import com.footystars.foot8.business.service.player.PlayersTransactionalService;
import com.footystars.foot8.exception.PlayerStatisticsException;
import com.footystars.foot8.exception.TeamInfoException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static com.footystars.foot8.utils.LogsNames.FETCHED_SUCCESSFULLY;
import static com.footystars.foot8.utils.LogsNames.FETCHING_PAGE_PLAYERS;
import static com.footystars.foot8.utils.LogsNames.NO_PLAYERS_FOUND;
import static com.footystars.foot8.utils.LogsNames.PLAYER_STATISTICS_EXCEPTION;
import static com.footystars.foot8.utils.ParameterName.LEAGUE;
import static com.footystars.foot8.utils.ParameterName.PAGE;
import static com.footystars.foot8.utils.ParameterName.SEASON;
import static com.footystars.foot8.utils.PathSegment.PLAYERS;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayersFetcher {

    private static final int MAX_REQUESTS_PER_MINUTE = 450;
    private static final int THREAD_POOL_SIZE = 20;

    private final ApiDataFetcher dataFetcher;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;
    private final PlayersTransactionalService playersTransactionalService;
    private final PlayerInfoService playerInfoService;

    private static final ConcurrentHashMap<Long, Lock> playerLocks = new ConcurrentHashMap<>();

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
            .build();

    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    @Async
    public void fetchByAllLeagues() {
        getTopLeaguesIds().forEach(this::fetchPlayersByLeagueId);
        log.info(FETCHED_SUCCESSFULLY);
    }

    @Async
    public void fetchCurrentSeasonTopLeaguesPlayers() {
        var leagueIds = getTopLeaguesIds();
        var futures = leagueIds.stream()
                .map(this::fetchCurrentSeasonPlayersByLeagueId)
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> log.info(FETCHED_SUCCESSFULLY));
    }

    @Async
    public CompletableFuture<Void> fetchCurrentSeasonPlayersByLeagueId(@NotNull Long leagueId) {
        return CompletableFuture.runAsync(() -> {
            try {
                var optionalSeasons = seasonService.findByLeagueId(leagueId);
                optionalSeasons.stream()
                        .filter(s -> s.getCoverage().isPlayers())
                        .forEach(s -> fetchPlayersByLeagueIdAndYear(leagueId, s.getYear()));
            } catch (Exception e) {
                throw new PlayerStatisticsException(PLAYER_STATISTICS_EXCEPTION + leagueId, e);
            }
        }, executorService);
    }

    @Async
    public void fetchPlayersByLeagueId(@NotNull Long leagueId) {
        try {
            var optionalSeasons = seasonService.findByLeagueId(leagueId);
            optionalSeasons.stream()
                    .filter(s -> s.getCoverage().isPlayers())
                    .forEach(s -> fetchPlayersByLeagueIdAndYear(leagueId, s.getYear()));
        } catch (Exception e) {
            throw new PlayerStatisticsException(PLAYER_STATISTICS_EXCEPTION + leagueId, e);
        }
    }

    @Async
    public CompletableFuture<Void> fetchPlayersByLeagueIdAndYear(@NotNull Long league, @NotNull Integer year) {
        return CompletableFuture.runAsync(() -> {
            try {
                var params = paramsProvider.getLeagueAndSeasonParamsMap(league, year);
                consumeBucket();
                var playersApiResponse = dataFetcher.fetch(PLAYERS, params, PlayersApiResponse.class);

                if (playersApiResponse != null && playersApiResponse.getResponse() != null) {
                    fetchResponsePages(params, playersApiResponse);
                } else {
                    log.warn(NO_PLAYERS_FOUND);
                }
            } catch (IOException | InterruptedException e) {
                log.error("Error fetching players for league {} in year {}: {}", league, year, e.getMessage());
                throw new TeamInfoException(e);
            }
        }, executorService);
    }

    @Async
    public CompletableFuture<Void> fetchResponsePages(@NotNull Map<String, String> params, @NotNull PlayersApiResponse playersApiResponse) {
        int totalPages = playersApiResponse.getPaging().getTotal();
        var response = playersApiResponse.getResponse();
        var year = params.get(SEASON);
        var leagueId = params.get(LEAGUE);

        response.forEach(this::processPlayer);

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 2; i <= totalPages; i++) {
            final int page = i;
            futures.add(CompletableFuture.runAsync(() -> {
                try {
                    params.put(PAGE, String.valueOf(page));
                    log.info(FETCHING_PAGE_PLAYERS, page, totalPages, year, leagueId);

                    consumeBucket();
                    var pageResponse = dataFetcher.fetch(PLAYERS, params, PlayersApiResponse.class);

                    if (pageResponse != null && pageResponse.getResponse() != null) {
                        pageResponse.getResponse().forEach(this::processPlayer);
                    }
                } catch (Exception e) {
                    log.error("Error fetching players on page {} for league {} in year {}: {}", page, leagueId, year, e.getMessage());
                }
            }, executorService));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Async
    public void processPlayer(@NotNull PlayerApi playerStatistics) {
        var playerId = playerStatistics.getPlayerInfo().getPlayerId();
        if (playerId != null) {
            Lock lock = playerLocks.computeIfAbsent(playerId, k -> new ReentrantLock());
            lock.lock();
            try {
                if (playersTransactionalService.existsById(playerId)) {
                    playersTransactionalService.updatePlayerTransactional(playerStatistics);
                } else {
                    playerInfoService.createPlayer(playerStatistics);
                }
            } catch (Exception e) {
                log.error("Error processing player with id {}: {}", playerId, e.getMessage());
            } finally {
                lock.unlock();
                playerLocks.remove(playerId);
            }
        }
    }

    private void consumeBucket() throws InterruptedException {
        bucket.asScheduler().consume(1);
    }
}