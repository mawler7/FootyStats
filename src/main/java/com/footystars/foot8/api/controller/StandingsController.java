package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.StandingsFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/standings")
public class StandingsController {

    private final StandingsFetcher standingsFetcher;

    private static final Logger logger = LoggerFactory.getLogger(StandingsController.class);

    @GetMapping("/{leagueId}")
    public void getStandingsByLeagueId(@PathVariable Long leagueId) {
        standingsFetcher.fetchStandingsByLeagueId(leagueId);
        logger.info("Fetched standings for leagueId {}", leagueId);
    }


    @GetMapping
    public void getStandings() {
        standingsFetcher.fetchStandings();
        logger.info("Fetched standings for leagues and cups");
    }

}
