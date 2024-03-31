package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.fixtures.Fixtures;
import com.footystars.foot8.buisness.service.FixtureService;
import com.footystars.foot8.buisness.service.SeasonService;
import com.footystars.foot8.exception.FixtureException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.PathSegment.FIXTURES;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;


@Service
@RequiredArgsConstructor
public class FixturesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final FixtureService fixtureService;
    private final SeasonService seasonService;

    @NotNull
    private static Map<String, String> createParamsMap(@NotNull Long leagueId, @NotNull int season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE,   leagueId.toString()) ;
        params.put(SEASON, String.valueOf(season)) ;
        return params;
    }

    public void fetchFixtures(@NotNull Long leagueId, @NotNull int season) throws FixtureException {
        try {
            var params = createParamsMap(leagueId, season);
            var fixtures = dataFetcher.fetch(FIXTURES, params, Fixtures.class).getFixtureList();
            fixtures.forEach(fixtureService::fetchFixture);
        } catch (IOException e) {
            throw new FixtureException(e, "Could not fetch fixtures");
        }
    }

    @Transactional
    public void fetchSelected() {
        var leagueId = PREMIER_LEAGUE.getId();
        var year = 2023;
        fetchFixtures(leagueId, year);


    }

    //    @Transactional
//    public void fetchSelected() {
//        var europeansTop5LeaguesIds = SelectedLeagues.getEuropeansTop5LeaguesIds();
//        europeansTop5LeaguesIds.forEach(leagueId -> {
//            var years = seasonService.findByLeagueId(leagueId);
//            years.forEach(year -> {
//                        try {
//                            fetchFixtures(leagueId, Long.valueOf(year));
//                        } catch (FixtureException e) {
//                            e.printStackTrace();
//                        }
//                    }
//            );
//        });
//    }


}