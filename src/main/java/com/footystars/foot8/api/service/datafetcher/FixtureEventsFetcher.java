package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.fixtures.events.Events;
import com.footystars.foot8.buisness.service.CompetitionService;
import com.footystars.foot8.buisness.service.FixtureEventsService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.FIXTURE;
import static com.footystars.foot8.utils.PathSegment.FIXTURES_EVENTS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class FixtureEventsFetcher {
    private final ApiDataFetcher dataFetcher;
    private final FixtureEventsService fixtureEventsService;
    private final CompetitionService competitionService;


    @NotNull
    private static Map<String, String> createParamsMap(@NotNull Long leagueId) {
        var params = new HashMap<String, String>();
        params.put(FIXTURE, leagueId.toString());
        return params;

    }

    public void fetchFixtureEvents(@NotNull Long fixtureId) {
        var params = createParamsMap(fixtureId);
        var events = dataFetcher.fetch(FIXTURES_EVENTS, params, Events.class).getResponse();
        if (events != null) {
            fixtureEventsService.fetchFixtureEvents(events, params);
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
                    fetchFixtureEvents(id);
                });
            }
        }
    }

}