package com.footystars.controller.api;

import com.footystars.service.api.TrophiesFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing trophies-related data retrieval.
 * Provides endpoints to fetch trophies won by players and coaches.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trophies")
@CrossOrigin(origins = "http://localhost:3000")
public class TrophiesController {

    private final TrophiesFetcher trophiesFetcher;

    /**
     * Fetches trophies won by all players.
     */
    @GetMapping("/players")
    public void fetchFavoritesPlayersByLeagueId() {
        trophiesFetcher.fetchPlayersTrophies();
    }

    /**
     * Fetches trophies won by players in a specific league.
     *
     * @param leagueId the ID of the league for which player trophies should be retrieved.
     */
    @GetMapping("/players/{leagueId}")
    public void fetchFavoritesPlayers(@PathVariable Long leagueId) {
        trophiesFetcher.fetchPlayersTrophiesByLeagueId(leagueId);
    }

    /**
     * Fetches trophies won by all coaches.
     */
    @GetMapping("/coaches")
    public void fetchFavoritesCoaches() {
        trophiesFetcher.fetchCoachesTrophies();
    }
}
