package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.league.LeaguesDto;
import com.footystars.foot8.buisness.service.LeagueService;
import com.footystars.foot8.exception.SaveLeagueException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterNames.ID;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.PathSegment.LEAGUES;
import static com.footystars.foot8.utils.Seasons.getAllSeasons;
import static com.footystars.foot8.utils.SelectedLeagues.getEuropeansTop5LeaguesIds;

@Service
@RequiredArgsConstructor
public class LeaguesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LeagueService leagueService;

    @Transactional
    public void fetchLeaguesForSeasonAndLeague(Long season, Long leagueId) {
        try {
            var params = new HashMap<String, String>();
            params.put(SEASON, String.valueOf(season));
            params.put(ID, String.valueOf(leagueId));
            var leaguesDto = dataFetcher.fetch(LEAGUES, params, LeaguesDto.class);
            var response = leaguesDto.getLeagues();
            response.forEach(leagueService::updateFromResponse);
        } catch (IOException e) {
            throw new SaveLeagueException("Could not save league with id: " + leagueId + "", e);
        }
    }

    @Transactional
    public void fetchTop5EuropeanLeagues() {
        getAllSeasons().forEach(s -> getEuropeansTop5LeaguesIds().forEach(l -> fetchLeaguesForSeasonAndLeague(Long.valueOf(s), l)));
    }
}

