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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.footystars.utils.ParameterName.PAGE;
import static com.footystars.utils.PathSegment.ODDS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

@RestController
@RequiredArgsConstructor
public class OddsFetcher {

    private static final int MAX_REQUESTS_PER_MINUTE = 450;

    private final ApiDataFetcher dataFetcher;
    private final BetsService oddsService;
    private final LeagueService leagueService;
    private final FixtureService fixtureService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(OddsFetcher.class);

    private static final ConcurrentHashMap<Long, Lock> oddsLock = new ConcurrentHashMap<>();


    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
            .build();

    @Async
    public void fetchByAllLeagues() {
        getTopLeaguesIds().forEach(this::fetchOddsByLeagueId);
    }

    @Async
    public void fetchOddsByLeagueId(@NotNull Long leagueId) {
        leagueService.findCurrentSeasonByLeagueId(leagueId).ifPresent(season -> {
            fetchOddsByLeagueAndYear(leagueId, season);
            logger.info("Odds fetched for leagueId: {}", leagueId);
        });
    }

    @Async
    public void fetchTodayOdds() {
        var fixtures = fixtureService.findTodayFixturesToUpdate();
        if (!fixtures.isEmpty()) {
            logger.info("Fetching {} of today's fixtures odds", fixtures.size());
            fixtures.forEach(f -> fetchOddsByFixtureId(f.getId()));
            logger.info("Fetched today fixtures odds");
        }
    }

    public void fetchOddsByFixtureId(@NotNull Long fixtureId) {
        if (bucket.tryConsume(1)) {
            var params = paramsProvider.getFixtureParamsMap(fixtureId);
            try {
                var odds = dataFetcher.fetch(ODDS, params, Odds.class);
                if (odds != null) {
                    processOdds(odds);
                }
            } catch (Exception e) {
                logger.error("Error fetching odds for fixtureId {}: {}", fixtureId, e.getMessage(), e);
            }
        }
    }

    public void fetchOddsByLeagueAndYear(@NotNull Long leagueId, @NotNull Integer year) {
        var params = paramsProvider.getOddsParamsMap(leagueId, year);
        if (bucket.tryConsume(1)) {
            try {
                var response = dataFetcher.fetch(ODDS, params, Odds.class);
                if (response != null) {
                    fetchResponsePage(params, response);
                }
            } catch (Exception e) {
                logger.error("Error fetching odds for leagueId {} and year {}: {}", leagueId, year, e.getMessage(), e);
            }
        }
    }

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
                logger.error("Thread was interrupted: {}", ie.getMessage(), ie);
                return;
            } catch (Exception e) {
                logger.error("Error processing page {}: {}", i, e.getMessage(), e);
            }
        }
    }

    public void processOdds(@NotNull Odds odds) {
        odds.getResponse().forEach(response -> {
            var fixtureId = response.getFixture().getFixtureId();
            if (fixtureId != null) {
                Lock lock = oddsLock.computeIfAbsent(fixtureId, k -> new ReentrantLock());
                lock.lock();
                try {
                    oddsService.updateOddsForFixture(response, fixtureId);
                } catch (Exception e) {
                    logger.error("Error processing odds for fixtureId {}: {}", fixtureId, e.getMessage(), e);
                } finally {
                    lock.unlock();
                    oddsLock.remove(fixtureId);
                }
            }
        });
    }

    private void consumeBucket() throws InterruptedException {
        bucket.asScheduler().consume(1);
    }

}


