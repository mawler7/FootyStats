package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.leagues.Leagues;
import com.footystars.foot8.buisness.service.LeagueService;
import com.footystars.foot8.exception.FetchLeaguesException;
import com.footystars.foot8.utils.SelectedLeagues;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterNames.ID;
import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.TYPE;
import static com.footystars.foot8.utils.PathSegment.LEAGUES;
import static com.footystars.foot8.utils.SelectedLeagues.getEuropeansTop5LeaguesIds;

@Service
@RequiredArgsConstructor
public class LeaguesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LeagueService leagueService;


    @Transactional
    public void fetchByType(String type)  {
        try {
            var params = new HashMap<String, String>();
            params.put(TYPE, String.valueOf(type));
            var leagues = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
            leagues.forEach(leagueService::fetchResponse);
        } catch (IOException e) {
            throw new FetchLeaguesException("Could not fetch leagues type: " + type, e);
        }
    }

    @Transactional
    public void fetchAll()  {
        try {
            var params = new HashMap<String, String>();
            var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
            leaguesDto.forEach(leagueService::fetchResponse);
        } catch (IOException e) {
            throw new FetchLeaguesException("Could not fetch leagues from the server!", e);
        }
    }

    @Transactional
    public void fetchSelected()  {
        try {
            var params = new HashMap<String, String>();
            params.put(ID, SelectedLeagues.PREMIER_LEAGUE.getId().toString());
            var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
            leaguesDto.forEach(leagueService::fetchResponse);
        } catch (IOException e) {
            throw new FetchLeaguesException("Could not fetch leagues from the server!", e);
        }
    }

}