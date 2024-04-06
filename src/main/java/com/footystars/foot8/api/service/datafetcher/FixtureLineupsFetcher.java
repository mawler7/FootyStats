package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.lineups.LineupsApi;
import com.footystars.foot8.buisness.service.CompetitionService;
import com.footystars.foot8.buisness.service.LineupsService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.FIXTURE;
import static com.footystars.foot8.utils.PathSegment.FIXTURES_LINEUPS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class FixtureLineupsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final CompetitionService competitionService;
    private final LineupsService fixtureLineupsService;

    @NotNull
    private static Map<String, String> createParamsMap(@NotNull Long fixtureId) {
        var params = new HashMap<String, String>();
        params.put(FIXTURE, fixtureId.toString());
        return params;
    }

    public void fetchFixtureLineups(@NotNull Long fixtureId) {
        var params = createParamsMap(fixtureId);
        var lineups = dataFetcher.fetch(FIXTURES_LINEUPS, params, LineupsApi.class).getResponse();
        if (!lineups.isEmpty()) {
            fixtureLineupsService.fetchFixtureLineups(lineups, params);
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
                    fetchFixtureLineups(id);
                });
            }
        }
    }

}
