package com.footystars.service.api;

import com.footystars.model.api.Standings;
import com.footystars.service.business.LeagueService;
import com.footystars.service.business.StandingsService;
import com.footystars.utils.ParamsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.footystars.utils.LogsNames.*;
import static com.footystars.utils.PathSegment.STANDINGS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching and storing league standings.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StandingsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final StandingsService standingsService;
    private final LeagueService leagueService;
    private final ParamsProvider paramsProvider;

    /**
     * Fetches standings for all top leagues asynchronously.
     */
    @Async
    public void fetchCurrentSeasonsStandings() {
        getTopLeaguesIds().parallelStream()
                .map(this::fetchTopLeaguesCurrentSeasonStandingsAsync)
                .forEach(CompletableFuture::join);
    }

    /**
     * Fetches standings for a specific league in the current season.
     *
     * @param leagueId The ID of the league.
     */
    public void fetchTopLeaguesCurrentSeasonStandings(@NotNull Long leagueId) {
        leagueService.findCurrentSeasonByLeagueId(leagueId)
                .ifPresent(season -> fetchStandings(leagueId, season));
    }

    /**
     * Fetches standings for a specific league asynchronously.
     *
     * @param leagueId The ID of the league.
     */
    @Async
    public void fetchStandingsByLeagueId(@NotNull Long leagueId) {
        leagueService.findCurrentSeasonByLeagueId(leagueId)
                .ifPresent(season -> fetchStandings(leagueId, season));

        log.info(STANDINGS_FETCHED_BY_ID, leagueId);
    }

    /**
     * Fetches standings for a given league and season.
     *
     * @param leagueId The ID of the league.
     * @param season   The season year.
     */
    public void fetchStandings(@NotNull Long leagueId, @NotNull Integer season) {
        try {
            var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, season);
            var standingsResponse = dataFetcher.fetch(STANDINGS, params, Standings.class).getResponse();

            standingsResponse.parallelStream()
                    .map(standings -> standings.getLeague().getStandings())
                    .filter(Objects::nonNull)
                    .forEach(standings -> standingsService.fetchStandings(standings, params));
        } catch (Exception e) {
            log.error(STANDINGS_ERROR, leagueId, season, e.getMessage(), e);
        }
    }

    /**
     * Asynchronously fetches standings for a given league in the current season.
     *
     * @param leagueId The ID of the league.
     * @return CompletableFuture for asynchronous execution.
     */
    @Async
    public CompletableFuture<Void> fetchTopLeaguesCurrentSeasonStandingsAsync(Long leagueId) {
        fetchTopLeaguesCurrentSeasonStandings(leagueId);
        return CompletableFuture.completedFuture(null);
    }
}
