package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.TeamFetcher;
import com.footystars.foot8.api.service.fetcher.TeamStatsFetcher;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TeamsController.class);

    @GetMapping("/info")
    public void getTeamsInfo() {
        teamFetcher.fetchByAllLeagues();
        logger.info("Fetched teams info");
    }

    @GetMapping("/info/current")
    public void getCurrentSeasonTeamsInfo() {
        teamFetcher.fetchCurrentSeasonTeamsInfo();
        logger.info("Fetched teams info for current season");
    }

    @GetMapping("/info/current/{leagueId}")
    public void getCurrentSeasonTeamsInfoByLeagueId(@PathVariable Long leagueId) {
        teamFetcher.fetchCurrentSeasonByLeagueId(leagueId);
        logger.info("Fetched teams info by leagueId {}", leagueId);
    }

    @GetMapping("/info/{leagueId}")
    public void getTeamsInfoByLeagueId(@PathVariable Long leagueId) {
        teamFetcher.fetchTeamsByLeagueId(leagueId);
        logger.info("Fetched teams info by leagueId {}", leagueId);
    }

    @GetMapping("/info/{leagueId}/{seasonYear}")
    public void getTeamsInfoByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        teamFetcher.fetchTeamInfoByLeagueAndSeason(leagueId, seasonYear);
        logger.info("Fetched teams info by leagueId {} and season {}", leagueId, seasonYear);
    }

    @GetMapping("/stats")
    public void getTeamsStats() {
        teamStatsFetcher.fetchByAllLeagues();
        logger.info("Fetched teams stats");
    }

    @GetMapping("/stats/current")
    public void getTeamsStatsCurrentSeason() {
        teamStatsFetcher.fetchCurrentSeasonsTeamStats();
        logger.info("Fetched teams stats");
    }

    @GetMapping("/stats/{leagueId}")
    public void getTeamsStatsFromSelectedLeagues(@PathVariable Long leagueId) {
        teamStatsFetcher.fetchByLeagueId(leagueId);
        logger.info("Fetched teams stats by leagueId {}", leagueId);
    }

}
