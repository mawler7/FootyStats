package com.footystars.service.api;

import com.footystars.exception.FixtureException;
import com.footystars.model.api.Fixtures;
import com.footystars.service.business.FixtureService;
import com.footystars.service.business.LeagueService;
import com.footystars.utils.ParamsProvider;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.footystars.utils.LogsNames.FIXTURES_FETCHED;
import static com.footystars.utils.LogsNames.FIXTURE_FETCHING_ERROR;
import static com.footystars.utils.LogsNames.FIXTURE_ID_FETCHING_ERROR;
import static com.footystars.utils.LogsNames.LEAGUE_SEASON_FETCHED;
import static com.footystars.utils.LogsNames.LIVE_FIXTURES_UPDATED;
import static com.footystars.utils.LogsNames.TODAY_FIXTURES_FETCHED;
import static com.footystars.utils.PathSegment.FIXTURES;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class FixturesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final FixtureService fixtureService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(FixturesFetcher.class);
    private final LeagueService leagueService;

    private Bucket createRateLimitBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
                .build();
    }

    private final Bucket bucket = createRateLimitBucket();

    @Async
    public void fetchAllSeasonsFixtures() {
        getTopLeaguesIds().forEach(this::fetchAllSeasonsFixturesByLeagueId);
        logger.info(FIXTURES_FETCHED);
    }

    public void fetchAllSeasonsFixturesByLeagueId(Long leagueId) {
        var leagues = leagueService.findByLeagueId(leagueId);
        leagues.parallelStream().forEach(league -> {
            var season = league.getSeason();
            try {
                fetchFixturesByLeagueAndSeason(leagueId, season.getYear());
                logger.info(LEAGUE_SEASON_FETCHED, season.getYear(), leagueId);
            } catch (Exception e) {
                throw new FixtureException("Could not fetch fixture for league :" + leagueId + " and season: " + season.getYear(), e);
            }
        });
    }

    @Async
    public void fetchCurrentSeasonFixtures() {
        getTopLeaguesIds().forEach(id -> {
            var seasonYear = leagueService.findCurrentSeasonByLeagueId(id);
            if (seasonYear.isPresent()) {
                var season = seasonYear.get();
                var ids = fixtureService.findFixtureIdsByLeagueIdAndSeason(id, season);
                ids.forEach(this::fetchFixtureById);
            }
        });
        logger.info("Fetched current season fixtures");
    }

    @Async
    public void updateLiveFixtures() {
        var fixtures = fixtureService.findLiveFixturesToUpdate();
        fixtures.forEach(f -> fetchFixtureById(f.getId()));
        if (!fixtures.isEmpty()) {
            logger.info(LIVE_FIXTURES_UPDATED, fixtures.size());
        }
    }

    @Async
    public void fetchTodayFixtures() {
        var fixtures = fixtureService.findFixtureIdsToUpdate();
        if (!fixtures.isEmpty()) {
            logger.info("Updating today fixtures: {}", fixtures.size());
            fixtures.forEach(this::fetchFixtureById);
            logger.info(TODAY_FIXTURES_FETCHED);

        }
    }

    @Async
    public void updateFixtures() {
        var ids = getTopLeaguesIds();
        ids.forEach(id -> {
            var fixtures = fixtureService.findCurrentSeasonFixtures();
            logger.info("Updating fixtures: {}", fixtures.size());
            fixtures.forEach(this::fetchFixtureById);
        });

    }

    public void fetchFixtureById(Long fixtureId) {
        if (bucket.tryConsume(1)) {
            try {
                var params = paramsProvider.getFixtureIdParamsMap(fixtureId);
                var fixtures = dataFetcher.fetch(FIXTURES, params, Fixtures.class).getFixturesList();
                fixtures.parallelStream().forEach(this::fetchFixture);
            } catch (Exception e) {
                logger.error(FIXTURE_ID_FETCHING_ERROR, fixtureId, e.getMessage());
            }
        } else {
            logger.warn("Request limit exceeded for fixtureId: {}", fixtureId);
        }
    }

    private boolean tryConsumeBucket() {
        if (!bucket.tryConsume(1)) {
            logger.warn("Request limit exceeded");
            return true;
        }
        return false;
    }

    public void fetchFixturesByLeagueAndSeason(Long leagueId, Integer year) {
        if (tryConsumeBucket()) return;

        try {
            var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, year);
            var fixtures = dataFetcher.fetch(FIXTURES, params, Fixtures.class).getFixturesList();
            fixtures.parallelStream().forEach(this::fetchFixture);
        } catch (Exception e) {
            logger.error(FIXTURE_FETCHING_ERROR, leagueId, year, e.getMessage());
        }
    }

    public void fetchFixture(@NotNull Fixtures.FixtureDto leagueFixture) {
        var fixtureId = leagueFixture.getInfo().getFixtureId();
        fixtureService.findById(fixtureId)
                .ifPresentOrElse(
                        fixture -> fixtureService.updateFixture(leagueFixture, fixture),
                        () -> fixtureService.createFixture(leagueFixture)
                );
    }

}


