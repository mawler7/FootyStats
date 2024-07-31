package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.fixtures.FixturesApi;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.fixture.FixtureService;
import com.footystars.foot8.exception.FixtureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

import static com.footystars.foot8.utils.LogsNames.ALL_FIXTURES_FETCHED;
import static com.footystars.foot8.utils.LogsNames.FIXTURE_FETCHING_ERROR;
import static com.footystars.foot8.utils.LogsNames.FIXTURE_ID_FETCHING_ERROR;
import static com.footystars.foot8.utils.LogsNames.LEAGUE_SEASON_FETCHED;
import static com.footystars.foot8.utils.PathSegment.FIXTURES;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class FixturesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final FixtureService fixtureService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(FixturesFetcher.class);

    public void fetchFixtureById(Long fixtureId) {
        try {
            var params = paramsProvider.getFixtureIdParamsMap(fixtureId);
            var fixtures = dataFetcher.fetch(FIXTURES, params, FixturesApi.class).getResponse();
            fixtures.forEach(fixtureService::fetchFixture);
        } catch (Exception e) {
            logger.error(FIXTURE_ID_FETCHING_ERROR, fixtureId, e.getMessage());
        }
    }

    public void fetchAllSeasonsFixturesByLeagueId(Long leagueId) {
        var seasonYears = seasonService.findByLeagueId(leagueId);

        seasonYears.forEach(season -> {
            try {
                fetchFixturesByLeagueAndSeason(leagueId, season.getYear());
                logger.info(LEAGUE_SEASON_FETCHED, season.getYear(), leagueId);
            } catch (Exception e) {
                throw new FixtureException("Could not fetch fixture for " +
                                        "league :" + leagueId + " and season: " + season.getYear(), e);
            }
        });
    }

    public void fetchFixturesByLeagueAndSeason(Long leagueId, Integer year) {
        try {
            var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, year);
            var fixtures = dataFetcher.fetch(FIXTURES, params, FixturesApi.class).getResponse();
            fixtures.forEach(fixtureService::fetchFixture);
        } catch (Exception e) {
            logger.error(FIXTURE_FETCHING_ERROR, leagueId, year, e.getMessage());
        }
    }

    public void fetchTopLeaguesAndCupsCurrentSeasonsNotFinishedFixtures() {
        var leaguesIds = getTopLeaguesIds();
        leaguesIds.parallelStream().forEach(leagueId -> {
            var fixtureList = fixtureService.findCurrentSeasonFixturesByLeagueId(leagueId);
            fixtureList.stream()
                    .filter(f -> !fixtureService.isFinalStatus(f.getStatus()))
                    .filter(f -> !f.getDate().isAfter(ZonedDateTime.now().plusDays(1)))
                    .forEach(f -> fetchFixtureById(f.getId()));
        });
        logger.info(ALL_FIXTURES_FETCHED);
    }


    public void fetchCurrentSeasonsFixturesByLeagueId(Long leagueId) {
        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);

        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get().getYear();
            fetchFixturesByLeagueAndSeason(leagueId, season);
            logger.info(LEAGUE_SEASON_FETCHED, season, leagueId);
        }
    }


    public void fetchFixturesByLeagueId(Long leagueId) {
        var seasons = seasonService.findByLeagueId(leagueId);
        seasons.forEach(s -> {
            var teams = s.getTeams();
            teams.forEach(team -> {
                var clubId = team.getClubId();
                fetchFixturesByTeamIdAndSeason(clubId, s.getYear());
            });
        });
    }

    private void fetchFixturesByTeamIdAndSeason(Long clubId, int year) {
        try {
            var params = paramsProvider.getTeamAndSeasonParamsMap(clubId, year);
            var fixtures = dataFetcher.fetch(FIXTURES, params, FixturesApi.class).getResponse();
            fixtures.forEach(fixtureService::fetchFixture);
        } catch (Exception e) {
            logger.error(FIXTURE_FETCHING_ERROR, clubId, year, e.getMessage());
        }
    }

    public void fetchTodayFixtures() {
        var leaguesIds = fixtureService.findTodayFixtures().stream()
                .map(Fixture::getId)
                .collect(Collectors.toSet());

        leaguesIds.parallelStream().forEach(id -> {
            var currentSeasonFixturesByLeagueId = fixtureService.findCurrentSeasonFixturesByLeagueId(id);
            currentSeasonFixturesByLeagueId.forEach(fixture -> fetchFixtureById(fixture.getId()));
        });
    }

    public void fetchTopLeaguesFixtures(Integer year, Long leagueId) {
        var leaguesIds = getTopLeaguesIds();

        leaguesIds.forEach(id -> {
            var fixtures = fixtureService.findByYearAndLeagueId(leagueId, year);
            fixtures.parallelStream().forEach(f -> fetchFixtureById(f.getId()));
        });
    }

}

