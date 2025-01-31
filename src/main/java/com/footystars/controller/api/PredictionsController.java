package com.footystars.controller.api;

import com.footystars.service.api.PredictionsFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing predictions-related data retrieval.
 * Provides endpoints to fetch match predictions for current and upcoming fixtures.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/predictions")
@CrossOrigin(origins = "http://localhost:3000")
public class PredictionsController {

    private final PredictionsFetcher predictionsFetcher;

    /**
     * Fetches match predictions for the current season in top leagues and cups.
     */
    @GetMapping("/current")
    public void getTopLeaguesAndCupsCurrentSeasonsFixturesPredictions() {
        predictionsFetcher.fetchCurrentSeasonPredictions();
    }

    /**
     * Fetches match predictions for the current season in a specific league.
     *
     * @param leagueId the ID of the league for which predictions should be retrieved.
     */
    @GetMapping("/current/{leagueId}")
    public void getCurrentSeasonFixturePredictionByLeagueId(@PathVariable Long leagueId) {
        predictionsFetcher.fetchCurrentSeasonPredictionsByLeagueId(leagueId);
    }

    /**
     * Fetches predictions for upcoming fixtures.
     */
    @GetMapping("/upcoming")
    public void getUpcomingPredictions() {
        predictionsFetcher.fetchUpcomingPredictions();
    }
}
