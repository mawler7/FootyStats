package com.footystars.controller;

import com.footystars.service.api.LeaguesFetcher;
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

    @GetMapping("/top")
    public void getSelectedLeagues() {
        leaguesFetcher.fetchTopLeaguesAndCups();
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
