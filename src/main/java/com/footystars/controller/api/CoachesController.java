package com.footystars.controller.api;

import com.footystars.service.api.CoachesFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling coach-related data retrieval.
 * Provides endpoints to fetch coaches for a specific league or the top leagues.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coaches")
@CrossOrigin(origins = "http://localhost:3000")
public class CoachesController {

    private final CoachesFetcher coachesFetcher;

    /**
     * Fetches coaches for teams in the specified league for the current season.
     *
     * @param leagueId the ID of the league for which coaches should be retrieved.
     */
    @GetMapping("/{leagueId}")
    public void getCoachesForSelectedTeams(@PathVariable Long leagueId) {
        coachesFetcher.fetchCoachesByLeagueIdInCurrentSeason(leagueId);
    }

    /**
     * Fetches coaches for teams in the top leagues for the current season.
     */
    @GetMapping
    public void getCurrentSeasonCoaches() {
        coachesFetcher.fetchTopLeaguesCoaches();
    }
}
