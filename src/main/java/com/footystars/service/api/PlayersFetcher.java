package com.footystars.service.api;

import com.footystars.exception.PlayerProcessingException;
import com.footystars.exception.TeamInfoException;
import com.footystars.model.api.Players;
import com.footystars.service.business.LeagueService;
import com.footystars.service.business.PlayerService;
import com.footystars.utils.ParamsProvider;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.footystars.utils.LogsNames.*;
import static com.footystars.utils.ParameterName.PAGE;
import static com.footystars.utils.PathSegment.PLAYERS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching player data from the external API.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PlayersFetcher {

    private static final int MAX_REQUESTS_PER_MINUTE = 450;

    private final ApiDataFetcher dataFetcher;
    private final ParamsProvider paramsProvider;
    private final PlayerService playerService;
    private final LeagueService leagueService;

    private static final ConcurrentHashMap<Long, Lock> playerLocks = new ConcurrentHashMap<>();

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
            .build();

    /**
     * Fetches players for all leagues asynchronously.
     */
    @Async
    public void fetchByAllLeagues() {
        getTopLeaguesIds().forEach(this::fetchPlayersByLeagueId);
        log.info(PLAYERS_FETCHED);
    }

    /**
     * Fetches players for the current season in top leagues asynchronously.
     */
    @Async
    public void fetchCurrentSeasonTopLeaguesPlayers() {
        getTopLeaguesIds().forEach(this::fetchCurrentSeasonPlayersByLeagueId);
        log.info(PLAYERS_CURRENT_FETCHED);
    }

    /**
     * Fetches players for the current season in a given league.
     *
     * @param leagueId The ID of the league.
     */
    @Async
    public void fetchCurrentSeasonPlayersByLeagueId(@NotNull Long leagueId) {
        leagueService.findCurrentSeasonByLeagueId(leagueId).ifPresent(season -> fetchPlayersByLeagueAndYear(leagueId, season));
    }

    /**
     * Fetches players for a given league.
     *
     * @param leagueId The ID of the league.
     */
    public void fetchPlayersByLeagueId(@NotNull Long leagueId) {
        leagueService.findByLeagueId(leagueId).forEach(league -> {
            try {
                fetchPlayersByLeagueAndYear(leagueId, league.getSeason().getYear());
                log.info(PLAYERS_FETCHED_LEAGUE_AND_SEASON, leagueId, league.getSeason().getYear());
            } catch (Exception e) {
                log.error("Error fetching players for league {} in year {}: {}", leagueId, league.getSeason().getYear(), e.getMessage());
            }
        });
    }

    /**
     * Fetches players for a given league and season.
     *
     * @param leagueId The league ID.
     * @param year     The season year.
     */
    public void fetchPlayersByLeagueAndYear(@NotNull Long leagueId, @NotNull Integer year) {
        var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, year);
        if (bucket.tryConsume(1)) {
            try {
                var playersApiResponse = dataFetcher.fetch(PLAYERS, params, Players.class);
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

    /**
     * Processes API response and fetches additional pages if available.
     *
     * @param params             API request parameters.
     * @param playersApiResponse Initial API response.
     */
    private void processPlayersResponse(@NotNull Map<String, String> params, @NotNull Players playersApiResponse) {
        int totalPages = playersApiResponse.getPaging().getTotal();
        var response = playersApiResponse.getResponse();

        response.forEach(this::processPlayer);

        for (int i = 2; i <= totalPages; i++) {
            params.put(PAGE, String.valueOf(i));
            if (bucket.tryConsume(1)) {
                try {
                    var pageResponse = dataFetcher.fetch(PLAYERS, params, Players.class);
                    if (pageResponse != null && pageResponse.getResponse() != null) {
                        pageResponse.getResponse().forEach(this::processPlayer);
                    }
                } catch (Exception e) {
                    log.error("Error fetching players on page {}: {}", i, e.getMessage());
                }
            }
        }
    }

    /**
     * Processes a single player from the API response.
     *
     * @param playerDto The player data transfer object.
     */
    public void processPlayer(@NotNull Players.PlayerDto playerDto) {
        var clubId = playerDto.getStatistics().get(0).getClub().getClubId();
        var playerId = playerDto.getInfo().getPlayerId();
        var leagueId = playerDto.getStatistics().get(0).getLeague().getLeagueId();
        var season = playerDto.getStatistics().get(0).getLeague().getSeason();

        if (playerId != null) {
            Lock lock = playerLocks.computeIfAbsent(playerId, k -> new ReentrantLock());
            boolean acquiredLock = false;

            try {
                acquiredLock = lock.tryLock(5, TimeUnit.SECONDS);

                if (acquiredLock) {
                    var optionalPlayer = playerService.findByIdLeagueIdSeasonAndClubId(playerId, leagueId, season, clubId);
                    if (optionalPlayer.isPresent()) {
                        var player = optionalPlayer.get();
                        playerService.updatePlayer(playerDto, player);
                    } else {
                        playerService.createPlayer(playerDto);
                    }
                } else {
                    log.warn("Failed to acquire lock for player id {} within timeout", playerId);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread was interrupted while waiting for lock on player id {}", playerId, e);
            } catch (Exception e) {
                log.error("Error processing player with id {}: {}", playerId, e.getMessage());
                throw new PlayerProcessingException("Error processing player with id " + playerId, e);
            } finally {
                if (acquiredLock) {
                    lock.unlock();
                    playerLocks.remove(playerId);
                }
            }
        }
    }
}
