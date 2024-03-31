package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.odds.Odds;
import com.footystars.foot8.buisness.service.OddsService;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.PAGE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.PathSegment.ODDS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@RestController
@RequiredArgsConstructor
public class OddsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final OddsService oddsService;

    private static final Log logger = LogFactory.getLog(OddsFetcher.class);

    @NotNull
    private static Map<String, String> createParamsMap(long league, long season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    @Transactional
    public void fetchOdds(long leagueId, long season) {
        try {
            var params = createParamsMap(leagueId, season);
            var response = dataFetcher.fetch(ODDS, params, Odds.class);
            var odds = response.getOddList();

            var pages = response.getPaging().getTotal();
            odds.forEach(oddsService::fetchOdds);
            for (int i = 2; i <= pages; i++) {
                params.put(PAGE, String.valueOf(i));
                var oddsList = dataFetcher.fetch(ODDS, params, Odds.class).getOddList();
                oddsList.forEach(oddsService::fetchOdds);
            }
        } catch (IOException e) {
            logger.error("Could not fetch odds!", e);
            throw new FetchLeaguesException("Could not fetch league from the server!", e);
        }

    }

    @Transactional
    public void fetchPremierLeagueCurrentSeason() {
        var leagueId = PREMIER_LEAGUE.getId();
        var season = 2023L;
        this.fetchOdds(leagueId, season);
    }
}