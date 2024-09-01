package com.footystars.service.api;

import com.footystars.model.api.Leagues;
import com.footystars.service.business.LeagueService;
import com.footystars.exception.DataFetcherException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.utils.ParameterName.ID;
import static com.footystars.utils.ParameterName.TYPE;
import static com.footystars.utils.PathSegment.LEAGUES;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class LeaguesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LeagueService leagueService;

    private final Logger logger = LoggerFactory.getLogger(LeaguesFetcher.class);

    @Async
    public void fetchTopLeaguesAndCups() {
        getTopLeaguesIds().forEach(this::fetchByLeagueId);
        logger.info("Fetched all leagues");
    }


    public void fetchByType(String type) {
        var params = new HashMap<String, String>();
        params.put(TYPE, String.valueOf(type));
        try {
            var leagues = dataFetcher.fetch(LEAGUES, params, Leagues.class).getResponse();
            leagues.forEach(leagueService::fetchLeague);
        } catch (IOException e) {
            throw new DataFetcherException("Error fetching leagues by type", e);
        }
    }


    public void fetchAll() {
        var params = new HashMap<String, String>();
        try {
            var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getResponse();
            leaguesDto.parallelStream().forEach(leagueService::fetchLeague);
        } catch (IOException e) {
            throw new DataFetcherException("Error fetching leagues", e);
        }
    }


    public void fetchByLeagueId(@NotNull Long leagueId) {
        var params = new HashMap<String, String>();
        params.put(ID, String.valueOf(leagueId));
        try {
            var response = dataFetcher.fetch(LEAGUES, params, Leagues.class).getResponse();
                response.parallelStream().forEach(leagueService::fetchLeague);

        } catch (IOException e) {
            throw new DataFetcherException("Error fetching leagues by leagueId", e);
        }
    }
}