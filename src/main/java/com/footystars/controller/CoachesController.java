package com.footystars.controller;

import com.footystars.service.api.CoachesFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coaches")
public class CoachesController {

    private final CoachesFetcher coachesFetcher;

    @GetMapping("/{leagueId}")
    public void getCoachesForSelectedTeams(@PathVariable Long leagueId) {
        coachesFetcher.fetchCoachesByLeagueIdInCurrentSeason(leagueId);
    }

    @GetMapping
    public void getCurrentSeasonCoaches() {
        coachesFetcher.fetchTopLeaguesCoaches();
    }

}
