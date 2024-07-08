package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.TrophiesFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trophies")
public class TrophiesController {

    private final TrophiesFetcher trophiesFetcher;

    @GetMapping("/players")
    public void fetchFavoritesPlayersByLeagueId() {
        trophiesFetcher.fetchFavoritesPlayers();
    }

    @GetMapping("/players/{leagueId}")
    public void fetchFavoritesPlayers(@PathVariable Long leagueId) {
        trophiesFetcher.fetchPlayersTrophiesByLeagueId(leagueId);
    }

    @GetMapping("/coaches")
    public void fetchFavoritesCoaches() {
        trophiesFetcher.fetchFavoritesCoaches();
    }

}
