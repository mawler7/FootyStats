package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.controller.OddsController;
import com.footystars.foot8.api.model.odds.OddsApi;
import com.footystars.foot8.api.model.odds.odd.Odd;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.FixtureService;
import com.footystars.foot8.business.service.OddsService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.utils.LogsNames;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.footystars.foot8.utils.ParameterName.LEAGUE;
import static com.footystars.foot8.utils.ParameterName.PAGE;
import static com.footystars.foot8.utils.ParameterName.SEASON;
import static com.footystars.foot8.utils.PathSegment.ODDS;
import static com.footystars.foot8.utils.SelectedLeagues.getFavoritesLeaguesAndCups;

@RestController
@RequiredArgsConstructor
public class OddsFetcher {

    private static final int MAX_REQUESTS_PER_MINUTE = 450;
    private static final int THREAD_POOL_SIZE = 20;

    private final ApiDataFetcher dataFetcher;
    private final OddsService oddsService;
    private final SeasonService seasonService;
    private final FixtureService fixtureService;

    private static final ConcurrentHashMap<Long, Lock> oddsLock = new ConcurrentHashMap<>();

    private final ParamsProvider paramsProvider;

    private static final Log logger = LogFactory.getLog(OddsFetcher.class);


    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
            .build();

    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);


    public void fetchByAllLeagues() {
        getFavoritesLeaguesAndCups()
                .forEach(this::fetchOddsByLeagueId);
    }

    @Async
    public void fetchOddsByLeagueId(@NotNull Long leagueId) {

        var optionalSeasons = seasonService.findByLeagueId(leagueId);
        optionalSeasons.stream().filter(s -> s.getCoverage().isOdds()).forEach(season -> {
            try {
                fetchOddsByLeagueAndYear(leagueId, season.getYear());
            } catch (Exception e) {
                logger.error((e.getMessage()));
            }
            logger.info("Odds fetching completed");
        });
    }

    @Async
    public void fetchOddsByLeagueAndYear(@NotNull Long leagueId, @NotNull Integer year) {
        try {
            var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, year);
            consumeBucket();
            var response = dataFetcher.fetch(ODDS, params, OddsApi.class);
            if (response != null && response.getResponse() != null) {
                fetchResponsePage(params, response);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void consumeBucket() throws InterruptedException {
        bucket.asScheduler().consume(1);
    }

    private void fetchResponsePage(@NotNull Map<String, String> params, @NotNull OddsApi oddsApi) {
        int totalPages = oddsApi.getPaging().getTotal();
        var response = oddsApi.getResponse();

        var year = params.get(SEASON);
        var leagueId = params.get(LEAGUE);

        response.forEach(this::processOdds);

        for (int i = 2; i <= totalPages; i++) {
            try {
                params.put(PAGE, String.valueOf(i));

                consumeBucket();
                var pageResponse = dataFetcher.fetch(ODDS, params, OddsApi.class);

                if (pageResponse != null && pageResponse.getResponse() != null) {
                    pageResponse.getResponse().forEach(this::processOdds);
                }
            } catch ( Exception e) {
                logger.error("Error fetching odds");
            }

        }
    }

    public void processOdds(@NotNull Odd odds) {
        var fixtureId = odds.getFixture().getFixtureId();
            Lock lock = oddsLock.computeIfAbsent(fixtureId, k -> new ReentrantLock());
            lock.lock();
            try {
                if (fixtureService.existsById(fixtureId)) {
                    oddsService.fetchOdds(odds);
                }
            } catch (Exception e) {
                logger.error((e.getMessage()));
            } finally {
                lock.unlock();
                oddsLock.remove(fixtureId);
            }
        }
    }


