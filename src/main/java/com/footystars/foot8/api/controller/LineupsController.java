package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.LineupsFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lineups")
public class LineupsController {

    private final LineupsFetcher fixtureLineupsFetcher;

    @GetMapping("/all/{leagueId}")
    public void getAllSeasonsLineupsForLeagueId(@PathVariable Long leagueId) {
        fixtureLineupsFetcher.fetchAllSeasonsLineupsByLeagueId(leagueId);
    }

    @GetMapping("/{leagueId}/{year}")
    public void getAllSeasonsLineupsForLeagueId(@PathVariable Long leagueId, @PathVariable Integer year) {
        fixtureLineupsFetcher.fetchByLeagueIdAndYear(leagueId,year);
    }

    @GetMapping
    public void getAllLineupsForSelectedLeagues() {
        fixtureLineupsFetcher.fetchFavorites();
    }


    @GetMapping("/current")
    public void getCurrentSeasonLineups() {
        fixtureLineupsFetcher.fetchFavorites();
    }

    @GetMapping("/{fixtureId}")
    public void getLineupsForFixtureId(@PathVariable Long fixtureId) {
        fixtureLineupsFetcher.fetchFixtureLineups(fixtureId);
    }

}
