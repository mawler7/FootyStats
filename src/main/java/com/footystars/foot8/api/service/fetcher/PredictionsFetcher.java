package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.predictions.PredictionResponse;
import com.footystars.foot8.api.service.params.ParamsProvider;
import com.footystars.foot8.business.service.FixtureService;
import com.footystars.foot8.business.service.PredictionService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.utils.FixtureStatuses;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;

import static com.footystars.foot8.utils.PathSegment.PREDICTIONS;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class PredictionsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PredictionService predictionService;
    private final FixtureService fixtureService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(PredictionsFetcher.class);

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
            .build();

    @Async
    @Transactional
    public void fetchFixturePrediction(Long fixtureId) {
        if (bucket.tryConsume(1)) {
        var param = paramsProvider.getFixtureParamsMap(fixtureId);
        try {
            var response = dataFetcher.fetch(PREDICTIONS, param, PredictionResponse.class);
            if (response != null) {
                var predictions = response.getResponse();
                predictions.forEach(p -> predictionService.fetchPredictions(p, fixtureId));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        } else {
            logger.warn("Request limit exceeded for fixtureId: {}", fixtureId);
        }
    }

@Transactional
    public void fetchByLeagueId(@NotNull Long leagueId) {
        var seasons = seasonService.findCurrentSeasonByLeagueId(leagueId);
        seasons.stream()
//                .filter(s -> s.getLeague().getId().equals(leagueId))
                .filter(s -> s.getCoverage().isPredictions())
                .forEach(s -> {
                    var fixtures = s.getFixtures();
                    fixtures.forEach(fixture ->
                            fetchFixturePrediction(fixture.getId()));
                });
    }


    @Transactional
    public void fetchCurrentSeasonPredictionsByLeagueId(@NotNull Long leagueId) {
        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            var fixtures = season.getFixtures();
            fixtures.stream()
                    .filter(f -> f.getStatus().equals(FixtureStatuses.NS.toString()))
                    .filter(f -> !f.getDate().isAfter(ZonedDateTime.now().plusDays(5)))
                    .forEach(fixture -> fetchFixturePrediction(fixture.getId()));
        }
    }


    @Async
    public void fetchCurrentSeasonPredictions() {
        var leaguesIds = getTopLeaguesIds();
        leaguesIds.parallelStream().forEach(this::fetchCurrentSeasonPredictionsByLeagueId);
    }

    @Async
    public void fetchTodayPredictions() {
        var fixtures = fixtureService.findTodayFixturesId();
        fixtures.forEach(this::fetchFixturePrediction);
    }
}

