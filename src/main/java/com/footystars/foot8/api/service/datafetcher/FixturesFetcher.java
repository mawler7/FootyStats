package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.fixture.FixturesResponse;
import com.footystars.foot8.buisness.service.FixtureService;
import com.footystars.foot8.exception.FixtureException;
import lombok.RequiredArgsConstructor;

import org.jetbrains.annotations.NotNull;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.PathSegment.FIXTURES;
import static com.footystars.foot8.utils.Seasons.getAllSeasons;
import static com.footystars.foot8.utils.SelectedLeagues.getEuropeansTop5LeaguesIds;


@Service
@RequiredArgsConstructor
public class FixturesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final FixtureService fixtureService;

    @NotNull
    private static Map<String, String> createParamsMap(Long league, Long season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    @Async
    public void fetchFixtures(@NotNull Long league, @NotNull Long season) throws FixtureException {
        try {
            var params = createParamsMap(league, season);
            var fixtures = dataFetcher.fetch(FIXTURES, params, FixturesResponse.class).getFixtures();
            fixtures.forEach(fixtureService::updateFromDto);
        } catch (IOException e) {
            throw new FixtureException(e, "Could not fetch fixtures");
        }
    }

    @Transactional
    public void fetchTop5Fixtures() {
        var allSeasons = getAllSeasons();
        var top5LeaguesIds = getEuropeansTop5LeaguesIds();
        allSeasons.forEach(s -> {
            try {
                top5LeaguesIds.forEach(l -> fetchFixtures(l, Long.valueOf(s)));
            } catch (FixtureException e) {
                e.printStackTrace();
            }
        });
    }

}
