package com.footystars.controller;

import com.footystars.service.api.FixturesFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fixtures")
public class FixturesController {

    private final FixturesFetcher fixturesFetcher;

    @GetMapping
    public void getAllFixtures() {
        fixturesFetcher.fetchAllSeasonsFixtures();
    }


    @GetMapping("/{leagueId}")
    public void getFixturesByLeagueId(@PathVariable Long leagueId) {
        fixturesFetcher.fetchAllSeasonsFixturesByLeagueId(leagueId);
    }

    @GetMapping("/current")
    public void getCurrentSeasonFixtures() {
        fixturesFetcher.fetchCurrentSeasonFixtures();
    }


    @GetMapping("/{leagueId}/{seasonYear}")
    public void getFixturesByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        fixturesFetcher.fetchFixturesByLeagueAndSeason(leagueId, seasonYear);
    }

}

