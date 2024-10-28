package com.footystars.service.api;

import com.footystars.model.api.Predictions;
import com.footystars.service.business.FixtureService;
import com.footystars.service.business.LeagueService;
import com.footystars.service.business.PredictionService;
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

import static com.footystars.utils.LogsNames.API_CALL_LIMIT_EXCEEDED;
import static com.footystars.utils.LogsNames.FETCHING_PREDICTIONS;
import static com.footystars.utils.LogsNames.PREDICTIONS_FETCHED;
import static com.footystars.utils.LogsNames.PREDICTIONS_FETCHED_LEAGUE;
import static com.footystars.utils.PathSegment.PREDICTIONS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class PredictionsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PredictionService predictionService;
    private final FixtureService fixtureService;
    private final LeagueService leagueService;
    private final ParamsProvider paramsProvider;
    private final Logger logger = LoggerFactory.getLogger(PredictionsFetcher.class);

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
            .build();

    @Async
    public void fetchCurrentSeasonPredictions() {
        getTopLeaguesIds().forEach(this::fetchCurrentSeasonPredictionsByLeagueId);
    }

    public void fetchCurrentSeasonPredictionsByLeagueId(@NotNull Long leagueId) {
        leagueService.findCurrentSeasonByLeagueId(leagueId).ifPresent(season -> {
            fixtureService.findByLeagueIdAndSeason(leagueId, season)
                    .forEach(fixture -> fetchFixturePrediction(fixture.getId()));
            logger.info(PREDICTIONS_FETCHED_LEAGUE, leagueId);
        });
    }

    @Async
    public void fetchUpcomingPredictions() {
        var fixturesId = fixtureService.findTodayFixturesId();
        logger.info(FETCHING_PREDICTIONS, fixturesId.size());
        fixturesId.forEach(this::fetchFixturePrediction);
        logger.info(PREDICTIONS_FETCHED);
    }

    @Async
    public void fetchArchivePredictions() {
        var ids = getTopLeaguesIds();
        ids.forEach(id -> {
            var leagues = leagueService.findByLeagueId(id);
            leagues.stream().filter(l -> l.getSeason().getCurrent().equals(Boolean.FALSE))
                    .forEach(l -> {
                var leagueId = l.getInfo().getLeagueId();
                var season = l.getSeason().getYear();
                        var fixtures = fixtureService.findByLeagueIdAndSeason(leagueId, season);
                        fixtures.forEach(fixture -> fetchFixturePrediction(fixture.getId()));
                    });

        });
        logger.info(PREDICTIONS_FETCHED);
    }

    public void fetchFixturePrediction(Long fixtureId) {
        if (bucket.tryConsume(1)) {
            var param = paramsProvider.getFixtureParamsMap(fixtureId);
            try {
                var response = dataFetcher.fetch(PREDICTIONS, param, Predictions.class);
                if (response != null) {
                    response.getPredictionsList().parallelStream()
                            .forEach(prediction -> predictionService.saveOrUpdatePrediction(prediction, fixtureId));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            logger.warn(API_CALL_LIMIT_EXCEEDED);
        }
    }

    @Async
    public void fetchPredictionsByLeagueId(@NotNull Long leagueId) {
        var fixtures = fixtureService.findByLeagueId(leagueId);
        fixtures.forEach(f-> {

        } );
    }
}

