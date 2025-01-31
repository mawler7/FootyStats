package com.footystars.service.api;

import com.footystars.model.api.Odds;
import com.footystars.service.business.BetsService;
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

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.footystars.utils.ParameterName.PAGE;
import static com.footystars.utils.PathSegment.ODDS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching and processing betting odds from the external API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OddsFetcher {

    private static final int MAX_REQUESTS_PER_MINUTE = 450;

    private final ApiDataFetcher dataFetcher;
    private final BetsService betsService;
    private final LeagueService leagueService;
    private final FixtureService fixtureService;
    private final ParamsProvider paramsProvider;

    private static final ConcurrentHashMap<Long, Lock> oddsLock = new ConcurrentHashMap<>();

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
            .build();

    /**
     * Fetches odds for all top leagues asynchronously.
     */
    @Async
    public void fetchByAllLeagues() {
        getTopLeaguesIds().forEach(this::fetchOddsByLeagueId);
    }

    /**
     * Fetches odds for a given league in the current season asynchronously.
     *
     * @param leagueId The ID of the league.
     */
    @Async
    public void fetchOddsByLeagueId(@NotNull Long leagueId) {
        leagueService.findCurrentSeasonByLeagueId(leagueId).ifPresent(season -> {
            fetchOddsByLeagueAndYear(leagueId, season);
            log.info("Odds fetched for leagueId: {}", leagueId);
        });
    }

    /**
     * Fetches odds for today's fixtures.
     */
    @Async
    public void fetchTodayOdds() {
        var fixtures = fixtureService.findTodayFixturesToUpdate();
        if (!fixtures.isEmpty()) {
            log.info("Fetching odds for {} of today's fixtures", fixtures.size());
            fixtures.forEach(f -> fetchOddsByFixtureId(f.getId()));
            log.info("Fetched today fixtures odds");
        }
    }

    /**
     * Fetches odds for a specific fixture.
     *
     * @param fixtureId The ID of the fixture.
     */
    public void fetchOddsByFixtureId(@NotNull Long fixtureId) {
        if (bucket.tryConsume(1)) {
            var params = paramsProvider.getFixtureParamsMap(fixtureId);
            try {
                var odds = dataFetcher.fetch(ODDS, params, Odds.class);
                if (odds != null) {
                    processOdds(odds);
                }
            } catch (Exception e) {
                log.error("Error fetching odds for fixtureId {}: {}", fixtureId, e.getMessage(), e);
            }
        }
    }

    /**
     * Fetches odds for a given league and season.
     *
     * @param leagueId The league ID.
     * @param year     The season year.
     */
    public void fetchOddsByLeagueAndYear(@NotNull Long leagueId, @NotNull Integer year) {
        var params = paramsProvider.getOddsParamsMap(leagueId, year);
        if (bucket.tryConsume(1)) {
            try {
                var response = dataFetcher.fetch(ODDS, params, Odds.class);
                if (response != null) {
                    fetchResponsePage(params, response);
                }
            } catch (Exception e) {
                log.error("Error fetching odds for leagueId {} and year {}: {}", leagueId, year, e.getMessage(), e);
            }
        }
    }

    /**
     * Fetches additional pages of odds data if available.
     *
     * @param params Request parameters.
     * @param odds   Initial odds response.
     */
    private void fetchResponsePage(@NotNull Map<String, String> params, @NotNull Odds odds) {
        int totalPages = odds.getPaging().getTotal();
        processOdds(odds);

        for (int i = 2; i <= totalPages; i++) {
            try {
                params.put(PAGE, String.valueOf(i));
                consumeBucket();
                var pageResponse = dataFetcher.fetch(ODDS, params, Odds.class);
                if (pageResponse != null) {
                    processOdds(pageResponse);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.error("Thread was interrupted: {}", ie.getMessage(), ie);
                return;
            } catch (Exception e) {
                log.error("Error processing page {}: {}", i, e.getMessage(), e);
            }
        }
    }

    /**
     * Processes and updates odds for fixtures.
     *
     * @param odds Odds data received from the API.
     */
    public void processOdds(@NotNull Odds odds) {
        odds.getResponse().forEach(response -> {
            var fixtureId = response.getFixture().getFixtureId();
            if (fixtureId != null) {
                Lock lock = oddsLock.computeIfAbsent(fixtureId, k -> new ReentrantLock());
                lock.lock();
                try {
                    betsService.updateOddsForFixture(response, fixtureId);
                } catch (Exception e) {
                    log.error("Error processing odds for fixtureId {}: {}", fixtureId, e.getMessage(), e);
                } finally {
                    lock.unlock();
                    oddsLock.remove(fixtureId);
                }
            }
        });
    }

    /**
     * Consumes a request from the API rate limiter.
     *
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    private void consumeBucket() throws InterruptedException {
        bucket.asScheduler().consume(1);
    }
}
