package com.footystars.service.api;

import com.footystars.model.api.Leagues;
import com.footystars.service.business.LeagueService;
import com.footystars.exception.DataFetcherException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.footystars.utils.LogsNames.*;
import static com.footystars.utils.ParameterName.ID;
import static com.footystars.utils.ParameterName.TYPE;
import static com.footystars.utils.PathSegment.LEAGUES;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching leagues from the external API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LeaguesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LeagueService leagueService;

    /**
     * Fetches data for top leagues and cups asynchronously.
     */
    @Async
    public void fetchTopLeaguesAndCups() {
        getTopLeaguesIds().forEach(this::fetchByLeagueId);
        log.info(LEAGUES_FETCHED);
    }

    /**
     * Fetches leagues based on their type (e.g., "league" or "cup").
     *
     * @param type The type of the league.
     */
    public void fetchByType(String type) {
        Map<String, String> params = new HashMap<>();
        params.put(TYPE, type);

        try {
            var leagues = dataFetcher.fetch(LEAGUES, params, Leagues.class).getResponse();
            leagues.forEach(leagueService::fetchLeague);
        } catch (IOException e) {
            log.error(LEAGUE_BY_TYPE_FETCHING_ERROR, type, e.getMessage(), e);
            throw new DataFetcherException(LEAGUE_BY_TYPE_FETCHING_ERROR, e);
        }
    }

    /**
     * Fetches all available leagues.
     */
    public void fetchAll() {
        Map<String, String> params = new HashMap<>();
        try {
            var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getResponse();
            leaguesDto.parallelStream().forEach(leagueService::fetchLeague);
        } catch (IOException e) {
            log.error(LEAGUES_FETCHING_ERROR, e.getMessage(), e);
            throw new DataFetcherException(LEAGUES_FETCHING_ERROR, e);
        }
    }

    /**
     * Fetches data for a specific league by its ID.
     *
     * @param leagueId The ID of the league.
     */
    public void fetchByLeagueId(@NotNull Long leagueId) {
        Map<String, String> params = new HashMap<>();
        params.put(ID, String.valueOf(leagueId));

        try {
            var response = dataFetcher.fetch(LEAGUES, params, Leagues.class).getResponse();
            response.forEach(leagueService::fetchLeague);
        } catch (IOException e) {
            log.error(LEAGUE_BY_LEAGUE_ID_FETCHING_ERROR,  e.getMessage(), e);
            throw new DataFetcherException(LEAGUE_BY_LEAGUE_ID_FETCHING_ERROR, e);
        }
    }
}
