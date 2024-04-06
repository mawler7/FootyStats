package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.predictions.PredictionResponse;
import com.footystars.foot8.buisness.service.CompetitionService;
import com.footystars.foot8.buisness.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.getPredictionsParam;
import static com.footystars.foot8.utils.PathSegment.PREDICTIONS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class PredictionsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PredictionService predictionService;
    private final CompetitionService competitionService;

    public void fetchFixturePrediction(Long fixtureId) {
        var param = getPredictionsParam();
        var response = dataFetcher.fetch(PREDICTIONS, Map.of(param, String.valueOf(fixtureId)), PredictionResponse.class);
        if (response != null) {
            var predictions = response.getResponse();
            predictions.forEach(p -> predictionService.fetchPredictions(p, fixtureId));
        }
    }

    public void fetchSelected() {
        var leagueId = PREMIER_LEAGUE.getId();
        var competitions = competitionService.getByLeagueId(leagueId);
        var optionalCompetition = competitions.stream().filter(c -> c.getSeason().getCurrent()).findFirst();
        if (optionalCompetition.isPresent()) {
            var competition = optionalCompetition.get();
            var fixtures = competition.getFixtures();
            if (!fixtures.isEmpty()) {
                fixtures.forEach(fixture -> {
                    var id = fixture.getId();
                    fetchFixturePrediction(id);
                });
            }
        }
    }

}


