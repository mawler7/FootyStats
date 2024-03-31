package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.standings.StandingsApi;
import com.footystars.foot8.buisness.service.StandingsService;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.PathSegment.STANDINGS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class StandingsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final StandingsService standingsService;

    @Transactional
    public void fetchSelected() {
        var leagueId = PREMIER_LEAGUE.getId();
        var season = 2023;
        try {
            var params = new HashMap<String, String>();
            params.put(LEAGUE, leagueId.toString());
            params.put(SEASON, String.valueOf(season));
            var standingApis = dataFetcher.fetch(STANDINGS, params, StandingsApi.class);
            standingsService.fetchStandings(standingApis, params);
        } catch (IOException e) {
            throw new FetchLeaguesException("Could not fetch league from the server!", e);
        }
    }

}
