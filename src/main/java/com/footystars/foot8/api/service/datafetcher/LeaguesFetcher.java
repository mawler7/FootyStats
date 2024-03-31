package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.leagues.Leagues;
import com.footystars.foot8.buisness.service.LeagueService;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterNames.ID;
import static com.footystars.foot8.utils.ParameterNames.TYPE;
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
        try {
            var params = new HashMap<String, String>();
            params.put(TYPE, String.valueOf(type));
            var leagues = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
            leagues.forEach(leagueService::fetchLeague);
        } catch (IOException e) {
            handleFetchError(e);
        }
    }

    @Transactional
    public void fetchAll() {
        try {
            var params = new HashMap<String, String>();
            var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
            leaguesDto.forEach(leagueService::fetchLeague);
        } catch (IOException e) {
            handleFetchError(e);
        }
    }

    @Transactional
    public void fetchSelected() {
        var leagueId = PREMIER_LEAGUE.getId();

        try {
            var params = new HashMap<String, String>();
            params.put(ID, leagueId.toString());
            var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
            if(leaguesDto != null) {
            leaguesDto.forEach(leagueService::fetchLeague);
            }
        } catch (IOException e) {
            handleFetchError(e);
        }
    }
    private void handleFetchError(IOException e) {
        logger.error("Could not fetch league from the server!", e);
        throw new FetchLeaguesException("Could not fetch league from the server!", e);
    }

//    @Transactional
//    public void fetchSelected() {
//        var europeansTop5LeaguesIds = SelectedLeagues.getEuropeansTop5LeaguesIds();
//        europeansTop5LeaguesIds.forEach(id -> {
//            try {
//                var params = new HashMap<String, String>();
//                params.put(ID, id.toString());
//                var leaguesDto = dataFetcher.fetch(LEAGUES, params, Leagues.class).getLeagueList();
//                leaguesDto.forEach(leagueService::fetchLeague);
//            } catch (IOException e) {
//                throw new FetchLeaguesException("Could not fetch leagues from the server!", e);
//            }
//        });
//    }
}