package com.footystars.controller.api;

import com.footystars.service.api.TeamFetcher;
import com.footystars.service.api.TeamStatsFetcher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamsController {

    private final TeamFetcher teamFetcher;
    private final TeamStatsFetcher teamStatsFetcher;

    @GetMapping("/info")
    public void getTeamsInfo() {
        teamFetcher.fetchByAllLeagues();
    }

    @GetMapping("/info/current")
    public void getCurrentSeasonTeamsInfo() {
        teamFetcher.fetchCurrentSeasonTeamsInfo();
    }

    @GetMapping("/info/current/{leagueId}")
    public void getCurrentSeasonTeamsInfoByLeagueId(@PathVariable Long leagueId) {
        teamFetcher.fetchCurrentSeasonByLeagueId(leagueId);
    }

    @GetMapping("/info/{leagueId}")
    public void getTeamsInfoByLeagueId(@PathVariable Long leagueId) {
        teamFetcher.fetchTeamsByLeagueId(leagueId);
    }

    @GetMapping("/info/{leagueId}/{seasonYear}")
    public void getTeamsInfoByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        teamFetcher.fetchTeamInfoByLeagueAndSeason(leagueId, seasonYear);
    }

    @GetMapping("/stats")
    public void getTeamsStats() {
        teamStatsFetcher.fetchTopLeaguesTeamsStatsByAllTime();
    }

    @GetMapping("/stats/current")
    public void getTeamsStatsCurrentSeason() {
        teamStatsFetcher.fetchCurrentSeasonsTeamStats();
    }

    @GetMapping("/stats/{leagueId}")
    public void getTeamsStatsFromSelectedLeagues(@PathVariable Long leagueId) {
        teamStatsFetcher.fetchAllTimeTeamsStatsByLeagueId(leagueId);
    }

}
