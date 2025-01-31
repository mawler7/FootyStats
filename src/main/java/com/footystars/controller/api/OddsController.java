package com.footystars.controller.api;

import com.footystars.service.api.OddsFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing odds-related data retrieval.
 * Provides endpoints to fetch betting odds by league, for today's fixtures, or for all leagues.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/odds")
@CrossOrigin(origins = "http://localhost:3000")
public class OddsController {

    private final OddsFetcher oddsFetcher;

    /**
     * Fetches betting odds for fixtures in a specific league.
     *
     * @param leagueId the ID of the league for which odds should be retrieved.
     */
    @GetMapping("/{leagueId}")
    public void getOddsByLeagueId(@PathVariable Long leagueId) {
        oddsFetcher.fetchOddsByLeagueId(leagueId);
    }

    /**
     * Fetches betting odds for today's fixtures.
     */
    @GetMapping("/today")
    public void getTodayFixturesOdds() {
        oddsFetcher.fetchTodayOdds();
    }

    /**
     * Fetches betting odds for fixtures across all leagues.
     */
    @GetMapping
    public void getFixturesOdds() {
        oddsFetcher.fetchByAllLeagues();
    }
}
