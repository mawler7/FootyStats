package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.fixtures.Fixtures;
import com.footystars.foot8.buisness.service.FixtureService;
import com.footystars.foot8.buisness.service.LeagueSeasonService;
import com.footystars.foot8.exception.FixtureException;
import lombok.RequiredArgsConstructor;

import org.jetbrains.annotations.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    private final LeagueSeasonService leagueSeasonService;

    @NotNull
    private static Map<String, String> createParamsMap(Long league, Long season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    public void fetchFixtures(@NotNull Long league, @NotNull Long season) throws FixtureException {
        try {
            var params = createParamsMap(league, season);
            var fixtures = dataFetcher.fetch(FIXTURES, params, Fixtures.class).getFixtureList();
            fixtures.forEach(fixtureService::fetchFixture);
        } catch (IOException e) {
            throw new FixtureException(e, "Could not fetch fixtures");
        }
    }

    @Transactional
    public void fetchSelected() {

        var leagueId = PREMIER_LEAGUE.getId();
        List<Integer> seasonsYears = List.of(2020, 2022);
//        List<Integer> seasonsYears = leagueSeasonService.getLeagueSeasonsYears(leagueId);
        try {
                seasonsYears.forEach(seasonYear -> fetchFixtures(leagueId, Long.valueOf(seasonYear)));
            } catch (FixtureException e) {
                e.printStackTrace();
            }
        }
    }

