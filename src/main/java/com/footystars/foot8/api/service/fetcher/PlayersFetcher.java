package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.players.PlayersApiResponse;
import com.footystars.foot8.api.model.players.player.PlayerApi;
import com.footystars.foot8.api.service.params.ParamsProvider;
import com.footystars.foot8.business.service.PlayerInfoService;
import com.footystars.foot8.business.service.PlayersTransactionalService;
import com.footystars.foot8.business.service.SeasonService;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.footystars.foot8.utils.ParameterName.PAGE;
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

    public void fetchByAllLeagues() {
        getTopLeaguesIds().forEach(this::fetchPlayersByLeagueId);
        log.info("All leagues fetched successfully.");
    }

    @Async
    public void fetchPlayersByLeagueId(@NotNull Long leagueId) {
        var seasons = seasonService.findByLeagueId(leagueId);
        seasons.stream()
                .filter(s -> s.getCoverage().isPlayers())
                .forEach(season -> {
                    try {
                        fetchPlayersByLeagueAndYear(leagueId, season.getYear());
                    } catch (Exception e) {
                        log.error("Error fetching players by league and year: {}", e.getMessage());
                    }
                });
        log.info("Players fetching completed for league {}", leagueId);
    }

    @Async
    public void fetchCurrentSeasonTopLeaguesPlayers() {
        getTopLeaguesIds().forEach(this::fetchCurrentSeasonPlayersByLeagueId);
        log.info("Current season players fetched successfully.");
    }

    @Async
    public void fetchCurrentSeasonPlayersByLeagueId(@NotNull Long leagueId) {
        var seasons = seasonService.findCurrentSeasonByLeagueId(leagueId);
        seasons.stream()
                .filter(s -> s.getCoverage().isPlayers())
                .forEach(season -> {
                    try {
                        fetchPlayersByLeagueAndYear(leagueId, season.getYear());
                    } catch (Exception e) {
                        log.error("Error fetching current season players for league {}: {}", leagueId, e.getMessage());
                        throw new PlayerStatisticsException("Failed to fetch players for league " + leagueId, e);
                    }
                });
    }

    public void fetchPlayersByLeagueAndYear(@NotNull Long leagueId, @NotNull Integer year) {
        var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, year);
        if (bucket.tryConsume(1)) {
            try {
                var playersApiResponse = dataFetcher.fetch(PLAYERS, params, PlayersApiResponse.class);
                if (playersApiResponse != null && playersApiResponse.getResponse() != null) {
                    processPlayersResponse(params, playersApiResponse);
                } else {
                    log.warn("No players found for league {} in year {}", leagueId, year);
                }
            } catch (IOException e) {
                log.error("Error fetching players for league {} in year {}: {}", leagueId, year, e.getMessage());
                throw new TeamInfoException(e);
            }
        }
    }

    private void processPlayersResponse(@NotNull Map<String, String> params, @NotNull PlayersApiResponse playersApiResponse) {
        int totalPages = playersApiResponse.getPaging().getTotal();
        var response = playersApiResponse.getResponse();

        response.forEach(this::processPlayer);

        for (int i = 2; i <= totalPages; i++) {
            params.put(PAGE, String.valueOf(i));
            if (bucket.tryConsume(1)) {
                try {
                    var pageResponse = dataFetcher.fetch(PLAYERS, params, PlayersApiResponse.class);
                    if (pageResponse != null && pageResponse.getResponse() != null) {
                        pageResponse.getResponse().forEach(this::processPlayer);
                    }
                } catch (Exception e) {
                    log.error("Error fetching players on page {}: {}", i, e.getMessage());
                }
            }
        }
    }

    public void processPlayer(@NotNull PlayerApi playerApi) {
        var playerId = playerApi.getPlayerInfo().getPlayerId();
        if (playerId != null) {
            Lock lock = playerLocks.computeIfAbsent(playerId, k -> new ReentrantLock());
            lock.lock();
            try {
                if (playersTransactionalService.existsById(playerId)) {
                    playersTransactionalService.updatePlayerTransactional(playerApi);
                } else {
                    playerInfoService.createPlayer(playerApi);
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