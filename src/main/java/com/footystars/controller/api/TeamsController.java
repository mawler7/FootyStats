package com.footystars.controller.api;

import com.footystars.service.api.TeamFetcher;
import com.footystars.service.api.TeamStatsFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing team-related data retrieval.
 * Provides endpoints to fetch team information and statistics by league and season.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@CrossOrigin(origins = "http://localhost:3000")
public class TeamsController {

    private final TeamFetcher teamFetcher;
    private final TeamStatsFetcher teamStatsFetcher;

    /**
     * Fetches information about teams from all leagues.
     */
    @GetMapping("/info")
    public void getTeamsInfo() {
        teamFetcher.fetchByAllLeagues();
    }

    /**
     * Fetches team information for the current season from all leagues.
     */
    @GetMapping("/info/current")
    public void getCurrentSeasonTeamsInfo() {
        teamFetcher.fetchCurrentSeasonTeamsInfo();
    }

    /**
     * Fetches team information for the current season in a specific league.
     *
     * @param leagueId the ID of the league.
     */
    @GetMapping("/info/current/{leagueId}")
    public void getCurrentSeasonTeamsInfoByLeagueId(@PathVariable Long leagueId) {
        teamFetcher.fetchCurrentSeasonByLeagueId(leagueId);
    }

    /**
     * Fetches team information for a specific league across all available seasons.
     *
     * @param leagueId the ID of the league.
     */
    @GetMapping("/info/{leagueId}")
    public void getTeamsInfoByLeagueId(@PathVariable Long leagueId) {
        teamFetcher.fetchTeamsByLeagueId(leagueId);
    }

    /**
     * Fetches team information for a specific league and season.
     *
     * @param leagueId   the ID of the league.
     * @param seasonYear the season year for which team information should be retrieved.
     */
    @GetMapping("/info/{leagueId}/{seasonYear}")
    public void getTeamsInfoByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        teamFetcher.fetchTeamInfoByLeagueAndSeason(leagueId, seasonYear);
    }

    /**
     * Fetches all-time team statistics for top leagues.
     */
    @GetMapping("/stats")
    public void getTeamsStats() {
        teamStatsFetcher.fetchTopLeaguesTeamsStatsByAllTime();
    }

    /**
     * Fetches team statistics for the current season.
     */
    @GetMapping("/stats/current")
    public void getTeamsStatsCurrentSeason() {
        teamStatsFetcher.fetchCurrentSeasonsTeamStats();
    }

    /**
     * Fetches all-time team statistics for a specific league.
     *
     * @param leagueId the ID of the league.
     */
    @GetMapping("/stats/{leagueId}")
    public void getTeamsStatsFromSelectedLeagues(@PathVariable Long leagueId) {
        teamStatsFetcher.fetchAllTimeTeamsStatsByLeagueId(leagueId);
    }
}
