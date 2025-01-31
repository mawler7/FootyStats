package com.footystars.service.api;

import com.footystars.exception.FixtureException;
import com.footystars.model.api.Fixtures;
import com.footystars.model.entity.Fixture;
import com.footystars.service.business.FixtureService;
import com.footystars.service.business.LeagueService;
import com.footystars.utils.ParamsProvider;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.footystars.utils.LogsNames.*;
import static com.footystars.utils.PathSegment.FIXTURES;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching fixtures from the external API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FixturesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final FixtureService fixtureService;
    private final ParamsProvider paramsProvider;
    private final LeagueService leagueService;

    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();


    private Bucket createRateLimitBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
                .build();
    }

    private final Bucket bucket = createRateLimitBucket();

    /**
     * Fetches all fixtures for top leagues across multiple seasons asynchronously.
     */
    @Async
    public void fetchAllSeasonsFixtures() {
        getTopLeaguesIds().forEach(this::fetchAllSeasonsFixturesByLeagueId);
        log.info(FIXTURES_FETCHED);
    }

    /**
     * Fetches all fixtures for a given league across all its seasons.
     *
     * @param leagueId The ID of the league.
     */
    public void fetchAllSeasonsFixturesByLeagueId(Long leagueId) {
        leagueService.findByLeagueId(leagueId).parallelStream().forEach(league -> {
            var seasonYear = league.getSeason().getYear();
            try {
                fetchFixturesByLeagueAndSeason(leagueId, seasonYear);
                log.info(LEAGUE_SEASON_FETCHED, seasonYear, leagueId);
            } catch (Exception e) {
                throw new FixtureException("Could not fetch fixture for league: " + leagueId + " and season: " + seasonYear, e);
            }
        });
    }

    /**
     * Fetches fixtures for the current season asynchronously.
     */
    @Async
    public void fetchCurrentSeasonFixtures() {
        getTopLeaguesIds().forEach(leagueId -> {
            Optional<Integer> seasonYear = leagueService.findCurrentSeasonByLeagueId(leagueId);
            seasonYear.ifPresent(year -> fetchFixturesByLeagueAndSeason(leagueId, year));
        });
        log.info("Fetched current season fixtures");
    }

    /**
     * Updates all live fixtures that require an update.
     */
    public void updateLiveFixtures() {
        try {
            List<Fixture> fixtures = fixtureService.findLiveFixturesToUpdate();
            if (fixtures == null || fixtures.isEmpty()) {
                log.warn("No live fixtures found to update.");
                return;
            }
            fixtures.stream().map(Fixture::getId).forEach(fixtureId -> {
                try {
                    fetchFixtureById(fixtureId);
                } catch (Exception e) {
                    log.error("Error updating fixture {}: {}", fixtureId, e.getMessage(), e);
                }
            });
            log.info(LIVE_FIXTURES_UPDATED, fixtures.size());
        } catch (Exception e) {
            log.error("Unexpected error in updateLiveFixtures: {}", e.getMessage(), e);
        }
    }

    /**
     * Updates fixture details for a specific league and season.
     *
     * @param leagueId The ID of the league.
     * @param year     The season year.
     */
    public void updateFixturesDetailsByLeagueIdAndSeason(Long leagueId, Integer year) {
        List<Long> fixtureIds = fixtureService.findFixtureIdsByLeagueIdAndSeason(leagueId, year);
        fixtureIds.forEach(this::fetchFixtureById);
        if (!fixtureIds.isEmpty()) {
            log.info(LIVE_FIXTURES_UPDATED, fixtureIds.size());
        }
    }

    /**
     * Updates fixtures that require status updates.
     */
    public void updateFixtures() {
        List<Long> fixtureIds = fixtureService.findFixtureIdsToUpdate();
        log.info("Updating fixtures: {}", fixtureIds.size());
        fixtureIds.forEach(this::fetchFixtureById);
        log.info("Fixtures updated");
    }

    /**
     * Fetches fixture details by fixture ID.
     *
     * @param fixtureId The ID of the fixture.
     */
    public void fetchFixtureById(Long fixtureId) {
        ReentrantLock lock = locks.computeIfAbsent(fixtureId, k -> new ReentrantLock());

        lock.lock();
        try {
            if (bucket.tryConsume(1)) {
                var params = paramsProvider.getFixtureIdParamsMap(fixtureId);
                var fixtures = dataFetcher.fetch(FIXTURES, params, Fixtures.class).getFixturesList();
                fixtures.forEach(this::fetchFixture);
            } else {
                log.warn("Request limit exceeded for fixtureId: {}", fixtureId);
            }
        } catch (IOException e) {
            log.error("Error fetching fixture {}: {}", fixtureId, e.getMessage(), e);
        } finally {
            lock.unlock();
            if (locks.containsKey(fixtureId)) {
                locks.remove(fixtureId, lock);
            }
        }
    }

    /**
     * Checks if the request bucket has available quota.
     *
     * @return true if request limit exceeded, otherwise false.
     */
    private boolean tryConsumeBucket() {
        if (!bucket.tryConsume(1)) {
            log.warn("Request limit exceeded");
            return true;
        }
        return false;
    }

    /**
     * Fetches fixtures for a specific league and season.
     *
     * @param leagueId The ID of the league.
     * @param year     The season year.
     */
    public void fetchFixturesByLeagueAndSeason(Long leagueId, Integer year) {
        if (tryConsumeBucket()) return;

        try {
            var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, year);
            var fixtures = dataFetcher.fetch(FIXTURES, params, Fixtures.class).getFixturesList();
            fixtures.forEach(this::fetchFixture);
        } catch (Exception e) {
            log.error(FIXTURE_FETCHING_ERROR, leagueId, year, e.getMessage(), e);
        }
    }

    /**
     * Processes a single fixture DTO by updating an existing fixture or creating a new one.
     *
     * @param leagueFixture The fixture DTO.
     */
    public void fetchFixture(@NotNull Fixtures.FixtureDto leagueFixture) {
        var fixtureId = leagueFixture.getInfo().getFixtureId();
        fixtureService.findById(fixtureId)
                .ifPresentOrElse(
                        fixture -> fixtureService.updateFixture(leagueFixture, fixture),
                        () -> fixtureService.createFixture(leagueFixture)
                );
    }

    /**
     * Updates fixture statuses that are outdated.
     */
    public void updateFixturesStatus() {
        fixtureService.findFixturesWithStatusNotToDate().forEach(this::fetchFixtureById);
        log.info("Updated fixtures status");
    }
}
