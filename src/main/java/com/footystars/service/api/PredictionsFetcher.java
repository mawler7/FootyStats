package com.footystars.service.api;

import com.footystars.model.api.Predictions;
import com.footystars.service.business.FixtureService;
import com.footystars.service.business.LeagueService;
import com.footystars.service.business.PredictionCheckerService;
import com.footystars.service.business.PredictionService;
import com.footystars.utils.ParamsProvider;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.footystars.utils.LogsNames.API_CALL_LIMIT_EXCEEDED;
import static com.footystars.utils.LogsNames.FETCHING_PREDICTIONS;
import static com.footystars.utils.LogsNames.PREDICTIONS_FETCHED;
import static com.footystars.utils.LogsNames.PREDICTIONS_FETCHED_LEAGUE;
import static com.footystars.utils.PathSegment.PREDICTIONS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching football match predictions from an external API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PredictionsFetcher {

    private static final int MAX_REQUESTS_PER_MINUTE = 450;

    private final ApiDataFetcher dataFetcher;
    private final PredictionService predictionService;
    private final FixtureService fixtureService;
    private final PredictionCheckerService predictionCheckerService;
    private final LeagueService leagueService;
    private final ParamsProvider paramsProvider;

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.greedy(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1))))
            .build();

    /**
     * Fetches predictions for the current season in all top leagues asynchronously.
     */
    @Async
    public void fetchCurrentSeasonPredictions() {
        getTopLeaguesIds().forEach(this::fetchCurrentSeasonPredictionsByLeagueId);
    }

    /**
     * Fetches predictions for the current season in a given league.
     *
     * @param leagueId The ID of the league.
     */
    public void fetchCurrentSeasonPredictionsByLeagueId(@NotNull Long leagueId) {
        leagueService.findCurrentSeasonByLeagueId(leagueId).ifPresent(season -> {
            var fixtures = fixtureService.findByLeagueIdAndSeason(leagueId, season);
            fixtures.parallelStream().forEach(fixture -> fetchFixturePrediction(fixture.getId()));
            log.info(PREDICTIONS_FETCHED_LEAGUE, leagueId);
        });
    }

    /**
     * Fetches predictions for upcoming fixtures asynchronously.
     */
    @Async
    public void fetchUpcomingPredictions() {
        var fixtureIds = fixtureService.findTodayFixturesId();
        log.info(FETCHING_PREDICTIONS, fixtureIds.size());

        fixtureIds.parallelStream()
                .map(this::fetchFixturePredictionAsync)
                .forEach(CompletableFuture::join);

        log.info(PREDICTIONS_FETCHED);
    }

    /**
     * Fetches predictions for a specific fixture.
     *
     * @param fixtureId The ID of the fixture.
     */
    public void fetchFixturePrediction(Long fixtureId) {
        if (!bucket.tryConsume(1)) {
            log.warn(API_CALL_LIMIT_EXCEEDED);
            return;
        }

        var params = paramsProvider.getFixtureParamsMap(fixtureId);
        try {
            var response = dataFetcher.fetch(PREDICTIONS, params, Predictions.class);
            if (response != null) {
                response.getPredictionsList()
                        .parallelStream()
                        .forEach(prediction -> predictionService.saveOrUpdatePrediction(prediction, fixtureId));
            }
        } catch (Exception e) {
            log.error("Error fetching predictions for fixture {}: {}", fixtureId, e.getMessage(), e);
        }
    }

    /**
     * Fetches predictions for a specific fixture asynchronously.
     *
     * @param fixtureId The ID of the fixture.
     * @return CompletableFuture for asynchronous execution.
     */
    @Async
    public CompletableFuture<Void> fetchFixturePredictionAsync(Long fixtureId) {
        fetchFixturePrediction(fixtureId);
        return CompletableFuture.completedFuture(null);
    }



}

