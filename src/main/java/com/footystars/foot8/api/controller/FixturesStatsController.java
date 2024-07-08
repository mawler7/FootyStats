package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.FixtureStatisticsFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fixtures/stats")
public class FixturesStatsController {

    private final FixtureStatisticsFetcher fixtureStatisticsFetcher;
    private static final Logger logger = LoggerFactory.getLogger(FixturesStatsController.class);
    @GetMapping("/{leagueId}")
    public void getFixtureStats(@PathVariable Long leagueId) {
        fixtureStatisticsFetcher.fetchByLeagueId(leagueId);
        logger.info("Fetched fixture stats for selected league");
    }

    @GetMapping("/{leagueId}/{year}")
    public void getFixtureStats(@PathVariable Long leagueId, @PathVariable Integer year) {
        fixtureStatisticsFetcher.fetchByLeagueAndYear(leagueId, year);
        logger.info("Fetched fixture stats for selected league");
    }


}
