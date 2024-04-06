package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.fixtures.statistics.FixtureStatistics;
import com.footystars.foot8.buisness.service.CompetitionService;
import com.footystars.foot8.buisness.service.FixtureStatisticsService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.FIXTURE;
import static com.footystars.foot8.utils.PathSegment.FIXTURES_STATISTICS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class FixtureStatisticsFetcher {
    private final ApiDataFetcher dataFetcher;
    private final FixtureStatisticsService fixtureStatisticsService;
    private final CompetitionService competitionService;

    @NotNull
    private static Map<String, String> createParamsMap(@NotNull Long fixtureId) {
        var params = new HashMap<String, String>();
        params.put(FIXTURE, fixtureId.toString());
        return params;
    }

    public void fetchFixtureStats(@NotNull Long fixtureId) {
        var params = createParamsMap(fixtureId);
        var statisticsApi = dataFetcher.fetch(FIXTURES_STATISTICS, params, FixtureStatistics.class).getResponse();
        if (statisticsApi != null) {
            fixtureStatisticsService.fetchFixtureStatistics(statisticsApi, params);
        }
    }

    public void fetchSelected() {
        var leagueId = PREMIER_LEAGUE.getId();
        var season = 2023;
        var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, season);
        if (optionalCompetition.isPresent()) {
            var competition = optionalCompetition.get();
            var fixtures = competition.getFixtures();
            if (!fixtures.isEmpty()) {
                fixtures.forEach(fixture -> {
                    var id = fixture.getId();
                    fetchFixtureStats(id);
                });
            }
        }
    }

}
