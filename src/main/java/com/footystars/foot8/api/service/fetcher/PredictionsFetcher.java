package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.predictions.PredictionResponse;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.PredictionService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.utils.FixtureStatuses;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.getFixturesParams;
import static com.footystars.foot8.utils.ParameterName.getPredictionsParam;
import static com.footystars.foot8.utils.PathSegment.PREDICTIONS;
import static com.footystars.foot8.utils.SelectedLeagues.getFavoritesLeaguesAndCups;
import static com.footystars.foot8.utils.SelectedLeagues.getSelectedCups;

@Service
@RequiredArgsConstructor
public class PredictionsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PredictionService predictionService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;


    public void fetchFixturePrediction(Long fixtureId) {
        var param = paramsProvider.getFixtureParamsMap(fixtureId);
        try {
            var response = dataFetcher.fetch(PREDICTIONS, param, PredictionResponse.class);
            if (response != null) {
                var predictions = response.getResponse();
                predictions.forEach(p -> predictionService.fetchPredictions(p, fixtureId));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void fetchByLeagueId(@NotNull Long leagueId) {
        var seasons = seasonService.findByLeagueId(leagueId);
        seasons.stream()
                .filter(s -> s.getLeague().getId().equals(leagueId))
                .filter(s-> s.getCoverage().isPredictions())
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
            var fixtures =season.getFixtures();
                fixtures.stream()
                        .filter(f -> f.getStatus().equals(FixtureStatuses.NS.toString()))
                        .forEach(fixture -> fetchFixturePrediction(fixture.getId()));
            }
        }


    public void fetchCurrentSeasonPredictions() {
        var leaguesIds = getFavoritesLeaguesAndCups();
        leaguesIds.forEach(this::fetchCurrentSeasonPredictionsByLeagueId);
    }
}

