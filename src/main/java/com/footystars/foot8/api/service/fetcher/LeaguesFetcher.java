package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.leagues.Leagues;
import com.footystars.foot8.business.service.LeagueService;
import com.footystars.foot8.exception.DataFetcherException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterName.ID;
import static com.footystars.foot8.utils.ParameterName.TYPE;
import static com.footystars.foot8.utils.PathSegment.LEAGUES;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class LeaguesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LeagueService leagueService;

    @Async
    public void fetchTopLeaguesAndCups() {
        getTopLeaguesIds().parallelStream().forEach(this::fetchByLeagueId);
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


    @Async
    public void fetchByLeagueId(@NotNull Long leagueId) {
        var params = new HashMap<String, String>();
        params.put(ID, String.valueOf(leagueId));
        try {
            var response = dataFetcher.fetch(LEAGUES, params, Leagues.class).getResponse();
            if (response != null) {
                response.parallelStream().forEach(leagueService::fetchLeague);
            }
        } catch (IOException e) {
            throw new DataFetcherException("Error fetching leagues by leagueId", e);
        }
    }
}