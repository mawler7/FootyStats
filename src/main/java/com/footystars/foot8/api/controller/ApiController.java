package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.datafetcher.CoachesFetcher;
import com.footystars.foot8.api.service.datafetcher.CountriesFetcher;
import com.footystars.foot8.api.service.datafetcher.FixtureEventsFetcher;
import com.footystars.foot8.api.service.datafetcher.FixtureLineupsFetcher;
import com.footystars.foot8.api.service.datafetcher.FixtureStatisticsFetcher;
import com.footystars.foot8.api.service.datafetcher.FixturesFetcher;
import com.footystars.foot8.api.service.datafetcher.LeaguesFetcher;
import com.footystars.foot8.api.service.datafetcher.OddsFetcher;
import com.footystars.foot8.api.service.datafetcher.PlayersFetcher;
import com.footystars.foot8.api.service.datafetcher.PredictionsFetcher;
import com.footystars.foot8.api.service.datafetcher.StandingsFetcher;
import com.footystars.foot8.api.service.datafetcher.TeamFetcher;
import com.footystars.foot8.api.service.datafetcher.TeamStatsFetcher;
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
    private final TeamFetcher teamInfoFetcher;
    private final TeamStatsFetcher teamStatsFetcher;
    private final CoachesFetcher coachesFetcher;
    private final OddsFetcher oddsFetcher;
    private final FixturesFetcher fixturesFetcher;
    private final PlayersFetcher playersFetcher;
    private final StandingsFetcher standingsFetcher;
    private final FixtureStatisticsFetcher fixtureStatisticsFetcher;
    private final FixtureEventsFetcher fixtureEventsFetcher;
    private final FixtureLineupsFetcher fixtureLineupsFetcher;
    private final PredictionsFetcher predictionsFetcher;

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/countries")
    public void getCountries() {
        countriesFetcher.fetchAllCountries();
    }

    @GetMapping("/leagues/all")
    public void getAllLeagues() {
        leaguesFetcher.fetchAll();
    }

    @GetMapping("/leagues/selected")
    public void getSelectedLeagues() {
        leaguesFetcher.fetchSelected();
    }

    @GetMapping("/leagues/{type}")
    public void getLeaguesByType(@PathVariable String type) {
        logger.info("Fetching leagues by type from the Server");
        leaguesFetcher.fetchByType(type);
    }

    @GetMapping("/teams/info/{leagueId}/{season}")
    public void getTeamsInfoByLeagueAndSeason(@PathVariable String leagueId, @PathVariable Integer season) {
        teamInfoFetcher.fetchTeamInfoByLeagueAndSeason(Long.valueOf(leagueId), season);
    }

    @GetMapping("/teams/info/selected")
    public void getTeamsInfoFromSelectedLeagues() {
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

    @GetMapping("/coaches/selected")
    public void getCoachesForSelectedTeams() {
        coachesFetcher.fetchSelected();
    }

    @GetMapping("/odds/selected")
    public void getOddsForSelectedTeams() {
        oddsFetcher.fetchPremierLeagueCurrentSeason();
    }

    @GetMapping("/standings/selected")
    public void getStandingsForSelectedLeague()   {
        standingsFetcher.fetchSelected();
    }

    @GetMapping("/fixture/stats/selected")
    public void getFixtureStats()   {
        fixtureStatisticsFetcher.fetchSelected();
    }

    @GetMapping("/fixture/events/selected")
    public void getFixtureEvents()   {
        fixtureEventsFetcher.fetchSelected();
    }

    @GetMapping("/fixture/lineups/selected")
    public void getFixtureLineups()   {
        fixtureLineupsFetcher.fetchSelected();
    }

    @GetMapping("/prediction/selected")
    public void getFixturePrediction()   {
        predictionsFetcher.fetchSelected();
    }

}
