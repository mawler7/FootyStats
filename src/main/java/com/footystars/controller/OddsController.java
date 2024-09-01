package com.footystars.controller;

import com.footystars.service.api.OddsFetcher;
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


    @GetMapping("/{leagueId}")
    public void getOddsByLeagueId(@PathVariable Long leagueId) {
        oddsFetcher.fetchOddsByLeagueId(leagueId);
    }

    @GetMapping("/today")
    public void getTodayFixturesOdds() {
        oddsFetcher.fetchTodayOdds();
    }

    @GetMapping()
    public void getFixturesOdds() {
        oddsFetcher.fetchByAllLeagues();
    }


}
