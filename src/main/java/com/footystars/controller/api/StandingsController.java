package com.footystars.controller.api;

import com.footystars.service.api.StandingsFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing league standings data retrieval.
 * Provides endpoints to fetch standings by league or for the current season.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/standings")
@CrossOrigin(origins = "http://localhost:3000")
public class StandingsController {

    private final StandingsFetcher standingsFetcher;

    /**
     * Fetches standings for a specific league.
     *
     * @param leagueId the ID of the league for which standings should be retrieved.
     */
    @GetMapping("/{leagueId}")
    public void getStandingsByLeagueId(@PathVariable Long leagueId) {
        standingsFetcher.fetchStandingsByLeagueId(leagueId);
    }

    /**
     * Fetches standings for all leagues in the current season.
     */
    @GetMapping
    public void getStandings() {
        standingsFetcher.fetchCurrentSeasonsStandings();
    }
}
