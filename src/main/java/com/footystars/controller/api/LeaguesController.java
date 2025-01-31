package com.footystars.controller.api;

import com.footystars.service.api.LeaguesFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing league-related data retrieval.
 * Provides endpoints to fetch leagues by various criteria.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leagues")
@CrossOrigin(origins = "http://localhost:3000")
public class LeaguesController {

    private final LeaguesFetcher leaguesFetcher;

    /**
     * Fetches top leagues and cups.
     */
    @GetMapping
    public void getTopLeagues() {
        leaguesFetcher.fetchTopLeaguesAndCups();
    }

    /**
     * Fetches all available leagues.
     */
    @GetMapping("/all")
    public void getAllLeagues()  {
        leaguesFetcher.fetchAll();
    }

    /**
     * Fetches leagues based on their type.
     *
     * @param type the type of leagues (e.g., domestic league, international cup).
     */
    @GetMapping("/type/{type}")
    public void getLeaguesByType(@PathVariable String type)  {
        leaguesFetcher.fetchByType(type);
    }

    /**
     * Fetches details for a specific league by its ID.
     *
     * @param leagueId the ID of the league to be retrieved.
     */
    @GetMapping("/{leagueId}")
    public void getLeaguesById(@PathVariable Long leagueId)  {
        leaguesFetcher.fetchByLeagueId(leagueId);
    }
}
