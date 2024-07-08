package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.LeaguesFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leagues")
public class LeaguesController {

    private final LeaguesFetcher leaguesFetcher;

    @GetMapping("/selected")
    public void getSelectedLeagues() {
        leaguesFetcher.fetchSelectedLeagues();
    }

    @GetMapping
    public void getAllLeagues()  {
        leaguesFetcher.fetchAll();
    }

    @GetMapping("/type/{type}")
    public void getLeaguesByType(@PathVariable String type)  {
        leaguesFetcher.fetchByType(type);
    }
    @GetMapping("/{leagueId}")
    public void getLeaguesById(@PathVariable Long leagueId)  {
        leaguesFetcher.fetchByLeagueId(leagueId);
    }

}
