package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.lineups.LineupsApi;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.model.entity.Season;
import com.footystars.foot8.business.service.LineupsService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.exception.FixturesLineupException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.footystars.foot8.utils.LogsNames.FIXTURE_COULD_NOT_BE_FETCHED;
import static com.footystars.foot8.utils.LogsNames.LEAGUE_SEASON_FETCHED;
import static com.footystars.foot8.utils.LogsNames.LINEUPS_LEAGUE_FETCHED;
import static com.footystars.foot8.utils.LogsNames.LINEUP_ERROR;
import static com.footystars.foot8.utils.PathSegment.FIXTURES_LINEUPS;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;


@Service
@RequiredArgsConstructor
public class LineupsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LineupsService lineupsService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(LineupsFetcher.class);


    public void fetchFavorites() {
        var allIds = getTopLeaguesIds();
        allIds.forEach(this::fetchByLeagueId);
    }


    public void fetchByLeagueId(@NotNull Long leagueId) {

        try {
            var optionalSeasons = seasonService.findByLeagueId(leagueId);

            optionalSeasons.stream()
                    .filter(season -> season.getCoverage().getFixtures().isLineups())
                    .forEach(season -> {
                fetchBySeason(season);

                logger.info(LINEUPS_LEAGUE_FETCHED, leagueId, season);
            });

        } catch (Exception e) {
            logger.error(LINEUP_ERROR, leagueId, e.getMessage());
        }
    }

    public void fetchByLeagueIdAndYear(@NotNull Long leagueId, @NotNull Integer year) {

        try {
            var optionalSeasons = seasonService.findByLeagueIdAndYear(leagueId, year);

            optionalSeasons.stream()
                    .filter(season -> season.getCoverage().getFixtures().isLineups())
                    .forEach(season -> {
                        fetchBySeason(season);

                        logger.info(LINEUPS_LEAGUE_FETCHED, leagueId, season);
                    });

        } catch (Exception e) {
            logger.error(LINEUP_ERROR, leagueId, e.getMessage());
        }
    }


    public void fetchBySeason(@NotNull Season season) {
        try {
            var fixtures = season.getFixtures();

            fixtures.parallelStream().forEach(fixture -> {
                var fixtureId = fixture.getId();
                fetchFixtureLineups(fixtureId);
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public void fetchFixtureLineups(@NotNull Long fixtureId) {
        var params = paramsProvider.getFixtureParamsMap(fixtureId);

        try {
            var lineups = dataFetcher.fetch(FIXTURES_LINEUPS, params, LineupsApi.class).getResponse();

            if (!lineups.isEmpty()) {
                lineupsService.fetchLineups(lineups, fixtureId);
            }
        } catch (IOException e) {
            throw new FixturesLineupException(e, FIXTURE_COULD_NOT_BE_FETCHED + fixtureId);
        }
    }

    public void fetchAllSeasonsLineupsByLeagueId(@NotNull Long leagueId) {
        var seasonYears = seasonService.findByLeagueId(leagueId);
        seasonYears.parallelStream().forEach(season -> {
            try {
                fetchBySeason(season);
                logger.info(LEAGUE_SEASON_FETCHED, leagueId, season);
            } catch (Exception e) {
                throw new FixturesLineupException(e, "Could not find lineups for league: " + leagueId + " in season: " + season);
            }
        });
    }
}