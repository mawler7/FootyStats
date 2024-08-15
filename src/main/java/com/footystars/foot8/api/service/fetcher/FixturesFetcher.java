package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.fixtures.FixturesApi;
import com.footystars.foot8.api.service.params.ParamsProvider;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.FixtureService;
import com.footystars.foot8.exception.FixtureException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;

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

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
            .build();


    @Async
    public void fetchAllSeasonsFixtures() {
        var ids = getTopLeaguesIds();
        ids.parallelStream().forEach(this::fetchAllSeasonsFixturesByLeagueId);
    }
    @Transactional
    @Async
    public void fetchAllSeasonsFixturesByLeagueId(Long leagueId) {
        var seasonYears = seasonService.findByLeagueId(leagueId);
        seasonYears.parallelStream().forEach(season -> {
            try {
                fetchFixturesByLeagueAndSeason(leagueId, season.getYear());
                logger.info(LEAGUE_SEASON_FETCHED, season.getYear(), leagueId);
            } catch (Exception e) {
                throw new FixtureException("Could not fetch fixture for " +
                        "league :" + leagueId + " and season: " + season.getYear(), e);
            }
        });
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
    @Async
    @Transactional
    public void fetchFixturesByLeagueAndSeason(Long leagueId, Integer year) {
        if (bucket.tryConsume(1)) {
            try {
                var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, year);
                var fixtures = dataFetcher.fetch(FIXTURES, params, FixturesApi.class).getResponse();
                fixtures.forEach(fixtureService::fetchFixture);
            } catch (Exception e) {
                logger.error(FIXTURE_FETCHING_ERROR, leagueId, year, e.getMessage());
            }
        } else {
            logger.warn("Request limit exceeded for leagueId: {}, season: {}", leagueId, year);
        }
    }



    @Async
    public void fetchTopLeaguesAndCupsCurrentSeasonsNotFinishedFixtures() {
        var leaguesIds = getTopLeaguesIds();
        leaguesIds.parallelStream().forEach(leagueId -> {
            var fixtureList = fixtureService.findCurrentSeasonFixturesByLeagueId(leagueId);
            fixtureList.stream()
//                    .filter(f -> fixtureService.isFinalStatus(f.getStatus()))
                    .filter(f -> !f.getDate().isAfter(ZonedDateTime.now().plusDays(2)))
                    .filter(f -> !f.getDate().isBefore(ZonedDateTime.now().minusDays(2)))
                    .forEach(f -> fetchFixtureById(f.getId()));
        });
        logger.info(ALL_FIXTURES_FETCHED);
    }

    public void fetchFixtureById(Long fixtureId) {
        if (bucket.tryConsume(1)) {
            try {
                var params = paramsProvider.getFixtureIdParamsMap(fixtureId);
                var fixtures = dataFetcher.fetch(FIXTURES, params, FixturesApi.class).getResponse();
                fixtures.forEach(fixtureService::fetchFixture);
            } catch (Exception e) {
                logger.error(FIXTURE_ID_FETCHING_ERROR, fixtureId, e.getMessage());
            }
        } else {
            logger.warn("Request limit exceeded for fixtureId: {}", fixtureId);
        }
    }

    private void fetchFixturesByTeamIdAndSeason(Long clubId, int year) {
        if (bucket.tryConsume(1)) {
            try {
                var params = paramsProvider.getTeamAndSeasonParamsMap(clubId, year);
                var fixtures = dataFetcher.fetch(FIXTURES, params, FixturesApi.class).getResponse();
                fixtures.forEach(fixtureService::fetchFixture);
            } catch (Exception e) {
                logger.error(FIXTURE_FETCHING_ERROR, clubId, year, e.getMessage());
            }
        } else {
            logger.warn("Request limit exceeded for teamId: {}, season: {}", clubId, year);
        }
    }

    @Async
    public void fetchTodayFixtures() {
        var fixturesId = fixtureService.findTodayFixturesId();

        fixturesId.forEach(this::fetchFixtureById);
    }

    public void fetchTopLeaguesFixtures(Integer year, Long leagueId) {
        var leaguesIds = getTopLeaguesIds();

        leaguesIds.forEach(id -> {
            var fixtures = fixtureService.findByYearAndLeagueId(leagueId, year);
            fixtures.parallelStream().forEach(f -> fetchFixtureById(f.getId()));
        });
    }

    @Async
    public void updateFixtures() {
        var fixturesToUpdate = fixtureService.findFixturesLessThen30minBeforeKickOff();

        logger.info("Updating {} fixtures", fixturesToUpdate.size());
        fixturesToUpdate.parallelStream().forEach(this::fetchFixtureById);
        logger.info("Fixtures updated successfully");
    }

}

