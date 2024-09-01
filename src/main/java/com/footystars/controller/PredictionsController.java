package com.footystars.controller;

import com.footystars.service.api.PredictionsFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/predictions")
public class PredictionsController {

    private final PredictionsFetcher predictionsFetcher;

    @GetMapping("/current")
    public void getTopLeaguesAndCupsCurrentSeasonsFixturesPredictions() {
        predictionsFetcher.fetchCurrentSeasonPredictions();
    }

    @GetMapping("/current/{leagueId}")
    public void getCurrentSeasonFixturePredictionByLeagueId(@PathVariable Long leagueId) {
        predictionsFetcher.fetchCurrentSeasonPredictionsByLeagueId(leagueId);
    }

    @GetMapping("/upcoming")
    public void getUpcomingPredictions() {
        predictionsFetcher.fetchUpcomingPredictions();
    }

}
