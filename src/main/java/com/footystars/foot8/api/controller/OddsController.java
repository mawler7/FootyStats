package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.OddsFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/odds")
public class OddsController {

    private final OddsFetcher oddsFetcher;

    private static final Logger logger = LoggerFactory.getLogger(OddsController.class);

    @GetMapping("/{leagueId}")
    public void getOddsByLeagueId(@PathVariable Long leagueId) {
        oddsFetcher.fetchOddsByLeagueId(leagueId);
        logger.info("Fetched odds for leagueOd {}", leagueId);
    }


}
