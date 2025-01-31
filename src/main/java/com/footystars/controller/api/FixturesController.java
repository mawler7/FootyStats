package com.footystars.controller.api;

import com.footystars.service.api.FixturesFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing fixture-related data retrieval and updates.
 * Provides endpoints to fetch fixtures based on league, season, and updates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fixtures")
@CrossOrigin(origins = "http://localhost:3000")
public class FixturesController {

    private final FixturesFetcher fixturesFetcher;

    /**
     * Fetches all fixtures across all seasons.
     */
    @GetMapping
    public void getAllFixtures() {
        fixturesFetcher.fetchAllSeasonsFixtures();
    }

    /**
     * Fetches all fixtures for a specific league across all seasons.
     *
     * @param leagueId the ID of the league for which fixtures should be retrieved.
     */
    @GetMapping("/{leagueId}")
    public void getFixturesByLeagueId(@PathVariable Long leagueId) {
        fixturesFetcher.fetchAllSeasonsFixturesByLeagueId(leagueId);
    }

    /**
     * Fetches fixtures for the current season.
     */
    @GetMapping("/current")
    public void getCurrentSeasonFixtures() {
        fixturesFetcher.fetchCurrentSeasonFixtures();
    }

    /**
     * Updates all fixtures.
     */
    @GetMapping("/update")
    public void updateFixtures() {
        fixturesFetcher.updateFixtures();
    }

    /**
     * Fetches and updates fixture details by its ID for the current season.
     *
     * @param id the ID of the fixture to be updated.
     */
    @GetMapping("/update/{id}")
    public void updateCurrentSeasonTodayFixtures(@PathVariable Long id) {
        fixturesFetcher.fetchFixtureById(id);
    }

    /**
     * Fetches fixtures for a specific league and season year.
     *
     * @param leagueId   the ID of the league.
     * @param seasonYear the season year for which fixtures should be retrieved.
     */
    @GetMapping("/{leagueId}/{seasonYear}")
    public void getFixturesByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        fixturesFetcher.fetchFixturesByLeagueAndSeason(leagueId, seasonYear);
    }

    /**
     * Fetches and updates fixture details for a specific league and season year.
     *
     * @param leagueId   the ID of the league.
     * @param seasonYear the season year for which fixture details should be updated.
     */
    @GetMapping("/update/{leagueId}/{seasonYear}")
    public void getFixturesDetailsByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        fixturesFetcher.updateFixturesDetailsByLeagueIdAndSeason(leagueId, seasonYear);
    }
}
