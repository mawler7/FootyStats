package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.datafetcher.CountriesFetcher;
import com.footystars.foot8.api.service.datafetcher.FixturesFetcher;
import com.footystars.foot8.api.service.datafetcher.LeaguesFetcher;
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
    private final VenuesFetcher venuesFetcher;
    private final FixturesFetcher fixturesFetcher;

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/countries")
    public void getCountries() throws CountryException {
        countriesFetcher.fetchCountries();
    }

    @GetMapping("/venues/top5")
    public void getTop5CountriesVenues() {
        venuesFetcher.fetchVenuesForTopFiveEuropeanLeagues();
    }

    @GetMapping("/leagues/top5")
    public void getTop5Leagues() {
        leaguesFetcher.fetchTop5EuropeanLeagues();
    }

    @GetMapping("/leagues/{leagueId}/{season}")
    public void getLeagueBySeasonAndLeagueId(@PathVariable Long leagueId, @PathVariable Long season) {
        leaguesFetcher.fetchLeaguesForSeasonAndLeague(leagueId, season);
    }

    @GetMapping("/teams/info/top5")
    public void getTop5TeamsInfo() {
        teamInfoFetcher.fetchTeamInfoFromTop5EuropeanLeagues();
    }

    @GetMapping("/teams/info/{season}/{leagueId}")
    public void getTeamsInfoBySeasonAndLeagueId(@PathVariable Long leagueId, @PathVariable Long season) {
        teamInfoFetcher.fetchTeamInfo(leagueId, season);
    }

    @GetMapping("/teams/stats/{teamId}/{season}/{leagueId}")
    public void getTeamsStatsBySeasonAndLeagueId(@PathVariable Long leagueId, @PathVariable Long season, @PathVariable Long teamId) {
        teamStatsFetcher.fetchTeamStatistics(teamId, leagueId, season);
    }

    @GetMapping("/teams/stats/top5")
    public void getTop5TeamsStats() {
        teamStatsFetcher.fetchTeamStatsFromTop5EuropeanLeagues();
    }


    @GetMapping("/fixtures/top5")
    public void fetchTop5Fixtures() {
        fixturesFetcher.fetchTop5Fixtures();
    }

}
