package com.footystars.controller.api;

import com.footystars.service.api.PlayersFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing player-related data retrieval.
 * Provides endpoints to fetch players by league, season, and top leagues.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayersController {

    private final PlayersFetcher playersFetcher;

    /**
     * Fetches players for a specific league and season year.
     *
     * @param leagueId   the ID of the league.
     * @param seasonYear the season year for which players should be retrieved.
     */
    @GetMapping("/{leagueId}/{seasonYear}")
    public void getPlayersByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        playersFetcher.fetchPlayersByLeagueAndYear(leagueId, seasonYear);
    }

    /**
     * Fetches players for a specific league across all available seasons.
     *
     * @param leagueId the ID of the league.
     */
    @GetMapping("/{leagueId}")
    public void getPlayersByLeagueId(@PathVariable Long leagueId) {
        playersFetcher.fetchPlayersByLeagueId(leagueId);
    }

    /**
     * Fetches players for the current season in top leagues.
     */
    @GetMapping("/current")
    public void getCurrentSeasonPlayers() {
        playersFetcher.fetchCurrentSeasonTopLeaguesPlayers();
    }

    /**
     * Fetches players from all leagues.
     */
    @GetMapping("/all")
    public void getAllPlayers() {
        playersFetcher.fetchByAllLeagues();
    }
}
