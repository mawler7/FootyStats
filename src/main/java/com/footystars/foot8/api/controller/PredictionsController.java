package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.PredictionsFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/predictions")
public class PredictionsController {

    private final PredictionsFetcher predictionsFetcher;

    private static final Logger logger = LoggerFactory.getLogger(PredictionsController.class);

    @GetMapping("/{leagueId}")
    public void getFixturePredictionByLeagueId(@PathVariable Long leagueId) {
        predictionsFetcher.fetchByLeagueId(leagueId);
    }

    @GetMapping("/current")
    public void getTopLeaguesAndCupsCurrentSeasonsFixturesPredictions() {
        predictionsFetcher.fetchCurrentSeasonPredictions();
    }

}
