package com.footystars.controller;

import com.footystars.service.api.StandingsFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/standings")
public class StandingsController {

    private final StandingsFetcher standingsFetcher;

    @GetMapping("/{leagueId}")
    public void getStandingsByLeagueId(@PathVariable Long leagueId) {
        standingsFetcher.fetchStandingsByLeagueId(leagueId);
    }

    @GetMapping
    public void getStandings() {
        standingsFetcher.fetchCurrentSeasonsStandings();
    }

}
