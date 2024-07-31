package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.predictions.PredictionResponse;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.PredictionService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.utils.FixtureStatuses;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static com.footystars.foot8.utils.PathSegment.PREDICTIONS;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class PredictionsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PredictionService predictionService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(PredictionsFetcher.class);

    public void fetchFixturePrediction(Long fixtureId) {
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
    }


    public void fetchByLeagueId(@NotNull Long leagueId) {
        var seasons = seasonService.findByLeagueId(leagueId);
        seasons.stream()
                .filter(s -> s.getLeague().getId().equals(leagueId))
                .filter(s -> s.getCoverage().isPredictions())
                .forEach(s -> {
                    var fixtures = s.getFixtures();
                    fixtures.forEach(fixture ->
                            fetchFixturePrediction(fixture.getId()));
                });
    }


    @Async
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


    public void fetchCurrentSeasonPredictions() {
        var leaguesIds = getTopLeaguesIds();
        leaguesIds.forEach(this::fetchCurrentSeasonPredictionsByLeagueId);
    }
}

