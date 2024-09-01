package com.footystars.controller;

import com.footystars.service.api.LineupsFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lineups")
public class LineupsController {

    private final LineupsFetcher fixtureLineupsFetcher;
//
//    @GetMapping("/all/{leagueId}")
//    public void getAllSeasonsLineupsForLeagueId(@PathVariable Long leagueId) {
//        fixtureLineupsFetcher.fetchAllSeasonsLineupsByLeagueId(leagueId);
//    }
//
//    @GetMapping("/{leagueId}/{year}")
//    public void getAllSeasonsLineupsForLeagueId(@PathVariable Long leagueId, @PathVariable Integer year) {
//        fixtureLineupsFetcher.fetchByLeagueIdAndYear(leagueId,year);
//    }
//
//    @GetMapping
//    public void getAllLineupsForSelectedLeagues() {
//        fixtureLineupsFetcher.fetchFavorites();
//    }
//
//
//    @GetMapping("/current")
//    public void getCurrentSeasonLineups() {
//        fixtureLineupsFetcher.fetchFavorites();
//    }
//
//    @GetMapping("/{fixtureId}")
//    public void getLineupsForFixtureId(@PathVariable Long fixtureId) {
//        fixtureLineupsFetcher.fetchFixtureLineups(fixtureId);
//    }

}
