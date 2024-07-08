package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.TeamFetcher;
import com.footystars.foot8.api.service.fetcher.TeamStatsFetcher;
import com.footystars.foot8.business.service.TeamService;
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
    private final TeamService teamService;

    private static final Logger logger = LoggerFactory.getLogger(TeamsController.class);

    @GetMapping("/info/{leagueId}")
    public void getTeamsInfoByLeagueId(@PathVariable Long leagueId) {
        teamFetcher.fetchByLeagueId(leagueId);
        logger.info("Fetched teams info by leagueId {}", leagueId);
    }

    @GetMapping("/info/{leagueId}/{seasonYear}")
    public void getTeamsInfoByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        teamFetcher.fetchTeamInfoByLeagueAndSeason(leagueId, seasonYear);
        logger.info("Fetched teams info by leagueId {} and season {}", leagueId, seasonYear);
    }

    @GetMapping("/info")
    public void getTeamsInfo() {
        teamFetcher.fetchByAllLeagues();
        logger.info("Fetched teams info");
    }


    @GetMapping("/stats/{leagueId}")
    public void getTeamsStatsFromSelectedLeagues(@PathVariable Long leagueId) {
        teamStatsFetcher.fetchByLeagueId(leagueId);
        logger.info("Fetched teams stats by leagueId {}", leagueId);
    }

    @GetMapping("/stats")
    public void getTeamsStats() {
        teamStatsFetcher.fetchByAllLeagues();
        logger.info("Fetched teams stats");
    }



}
