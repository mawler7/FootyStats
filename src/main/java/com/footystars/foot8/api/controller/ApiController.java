package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.datafetcher.CoachesFetcher;
import com.footystars.foot8.api.service.datafetcher.CountriesFetcher;
import com.footystars.foot8.api.service.datafetcher.FixturesFetcher;
import com.footystars.foot8.api.service.datafetcher.LeaguesFetcher;
import com.footystars.foot8.api.service.datafetcher.PlayersFetcher;
import com.footystars.foot8.api.service.datafetcher.TeamInfoFetcher;
import com.footystars.foot8.api.service.datafetcher.TeamStatsFetcher;
import com.footystars.foot8.api.service.datafetcher.VenuesFetcher;

import com.footystars.foot8.exception.CountryException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final CountriesFetcher countriesFetcher;
    private final LeaguesFetcher leaguesFetcher;
    private final TeamInfoFetcher teamInfoFetcher;
    private final TeamStatsFetcher teamStatsFetcher;
    private final CoachesFetcher coachesFetcher;
    private final VenuesFetcher venuesFetcher;
    private final FixturesFetcher fixturesFetcher;
    private final PlayersFetcher playersFetcher;

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    @GetMapping("/countries")
    public void getCountries() throws CountryException {
        countriesFetcher.fetchCountries();
    }

    @GetMapping("/leagues/all")
    public void getLeagues() {
        logger.info("Fetching all leagues from the Server");
        leaguesFetcher.fetchSelected();
    }

    @GetMapping("/teams/info/selected")
    public void getTop5TeamsInfo() {
        teamInfoFetcher.fetchSelectedLeaguesTeamsInfo();
    }

    @GetMapping("/teams/stats/top5")
    public void getTop5TeamsStats() {
        teamStatsFetcher.fetchSelectedLeaguesTeamsStats();
    }


    @GetMapping("/players/selected")
    public void fetchPlayersFromTop5() {
        playersFetcher.fetchSelectedLeaguesPlayers();
    }

    @GetMapping("/fixtures/selected")
    public void fetchTop5Fixtures() {
        fixturesFetcher.fetchSelected();
    }
//
//    @GetMapping("/coaches/selected")
//    public void getCoachesForSelectedTeams() {
//        coachesFetcher.fetchSelected();
//    }

//    @GetMapping("/teams/stats/{teamId}/{season}/{leagueId}")
//    public void getTeamsStatsBySeasonAndLeagueId(@PathVariable Long leagueId, @PathVariable Long season, @PathVariable Long teamId) {
//        teamStatsFetcher.fetchTeamStatistics(teamId, leagueId, season);
//    }



}
