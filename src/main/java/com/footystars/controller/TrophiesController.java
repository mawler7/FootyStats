package com.footystars.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trophies")
public class TrophiesController {

//    private final TrophiesFetcher trophiesFetcher;
//
//    @GetMapping("/players")
//    public void fetchFavoritesPlayersByLeagueId() {
//        trophiesFetcher.fetchFavoritesPlayers();
//    }
//
//    @GetMapping("/players/{leagueId}")
//    public void fetchFavoritesPlayers(@PathVariable Long leagueId) {
//        trophiesFetcher.fetchPlayersTrophiesByLeagueId(leagueId);
//    }
//
//    @GetMapping("/coaches")
//    public void fetchFavoritesCoaches() {
//        trophiesFetcher.fetchFavoritesCoaches();
//    }

}
