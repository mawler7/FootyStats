package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.FixturesFetcher;
import com.footystars.foot8.scheduler.DataFetcherScheduler;
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
    private final DataFetcherScheduler dataFetcherScheduler;

    @GetMapping("/{leagueId}")
    public void getFixturesByLeagueId(@PathVariable Long leagueId) {
        fixturesFetcher.fetchAllSeasonsFixturesByLeagueId(leagueId);
    }

    @GetMapping
    public void getFixtures() {
        fixturesFetcher.fetchAllSeasonsFixtures();
    }

    @GetMapping("/current")
    public void getCurrentSeasonFixturesForSelectedLeagues() {
        fixturesFetcher.fetchTopLeaguesAndCupsCurrentSeasonsNotFinishedFixtures();
    }

    @GetMapping("/{leagueId}/{seasonYear}")
    public void getFixturesByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        fixturesFetcher.fetchFixturesByLeagueAndSeason(leagueId, seasonYear);
    }

    @GetMapping("/team/{teamId}")
    public void getFixturesByTeamId(@PathVariable Long teamId) {
        fixturesFetcher.fetchFixturesByLeagueId(teamId);
    }

    @GetMapping("/today")
    public void getTodayFixtures() {
        fixturesFetcher.fetchTodayFixtures();
    }

    @GetMapping("/{year}/{leagueId}/")
    public void getFixtures(@PathVariable Integer year, @PathVariable Long leagueId) {
        fixturesFetcher.fetchTopLeaguesFixtures(year, leagueId);
    }

    @GetMapping("/update")
    public void updateFixtures() {
        dataFetcherScheduler.updateMatchData();
        fixturesFetcher.updateFixtures();
    }

}