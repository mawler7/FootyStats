package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.fixtures.events.Events;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.FixtureEventsService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.exception.FixtureEventsException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.footystars.foot8.utils.LogsNames.EVENTS_FETCHING;
import static com.footystars.foot8.utils.LogsNames.EVENTS_UPDATED;
import static com.footystars.foot8.utils.LogsNames.FIXTURE_EVENTS_COULD_NOT_BE_FETCHED;
import static com.footystars.foot8.utils.LogsNames.FIXTURE_EVENTS_FETCHED;
import static com.footystars.foot8.utils.LogsNames.FIXTURE_LINEUP_FETCHED;
import static com.footystars.foot8.utils.PathSegment.FIXTURES_EVENTS;
import static com.footystars.foot8.utils.SelectedLeagues.getFavoritesLeaguesAndCups;

@Service
@RequiredArgsConstructor
public class EventsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final FixtureEventsService fixtureEventsService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(EventsFetcher.class);

    public void fetchEventsFroAllSelectedLeagues() {
        var leaguesIds = getFavoritesLeaguesAndCups();
        leaguesIds.forEach(this::fetchEventsForAllSeasonsByLeagueId);
        logger.info(EVENTS_UPDATED);
    }

    public void fetchEventsForAllSeasonsByLeagueId(@NotNull Long leagueId) {
        var optionalSeasons = seasonService.findByLeagueId(leagueId);

        optionalSeasons.parallelStream().forEach(season -> {
            logger.info(EVENTS_FETCHING, leagueId, season);
            var fixtures = season.getFixtures();

            fixtures.forEach(fixture -> {
                var fixtureId = fixture.getId();
                fetchFixtureEventsByFixtureId(fixtureId);
            });
        });
    }

    public void fetchFixtureEventsByFixtureId(@NotNull Long fixtureId) {

        try {
            var params = paramsProvider.getFixtureParamsMap(fixtureId);
            var events = dataFetcher.fetch(FIXTURES_EVENTS, params, Events.class).getResponse();

            if (events != null) {
                fixtureEventsService.fetchFixtureEvents(events, fixtureId);
            }

        } catch (IOException e) {
            throw new FixtureEventsException(e, FIXTURE_EVENTS_COULD_NOT_BE_FETCHED + fixtureId);
        }
    }

    public void fetchEventsForCurrentSeason() {
        var leaguesIds = getFavoritesLeaguesAndCups();
        leaguesIds.forEach(this::fetchEventsCurrentSeasonLeagueId);
    }

    @Async
    public void fetchEventsCurrentSeasonLeagueId(@NotNull Long leagueId) {

        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);

        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get().getYear();
            fetchEventsByLeagueIdAndSeason(leagueId, season);
        }
    }


    @Async
    public void fetchEventsByLeagueIdAndSeason(@NotNull Long leagueId, @NotNull Integer year) {

        var optionalSeason = seasonService.findByLeagueIdAndYear(leagueId, year);

        if (optionalSeason.isPresent()) {
            var competition = optionalSeason.get();
            var fixtures = competition.getFixtures();

            if (!fixtures.isEmpty()) {
                fixtures.parallelStream().forEach(fixture -> {
                    var fixtureId = fixture.getId();

                    fetchFixtureEventsByFixtureId(fixtureId);
                    logger.info(FIXTURE_EVENTS_FETCHED, fixtureId);
                });
            }
        }
    }

}
