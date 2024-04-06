package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.leagues.Leagues;
import com.footystars.foot8.buisness.service.LeagueService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterName.ID;
import static com.footystars.foot8.utils.ParameterName.TYPE;
import static com.footystars.foot8.utils.PathSegment.LEAGUES;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class LeaguesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LeagueService leagueService;

    private final Log logger = LogFactory.getLog(LeaguesFetcher.class);

    @Transactional
    public void fetchByType(String type) {
        var params = new HashMap<String, String>();
        params.put(TYPE, String.valueOf(type));
        var leagues = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
        leagues.forEach(leagueService::fetchLeague);
    }

    @Transactional
    public void fetchAll() {
        var params = new HashMap<String, String>();
        var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
        leaguesDto.forEach(leagueService::fetchLeague);

    }

    @Transactional
    public void fetchSelected() {
        var leagueId = PREMIER_LEAGUE.getId();
        var params = new HashMap<String, String>();
        params.put(ID, leagueId.toString());
        var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
        if (leaguesDto != null) {
            leaguesDto.forEach(leagueService::fetchLeague);

        }
    }
}