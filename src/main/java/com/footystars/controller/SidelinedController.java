package com.footystars.controller;

import com.footystars.service.api.SidelinedFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sidelined")
public class SidelinedController {

    private final SidelinedFetcher sidelinedFetcher;
//
//    @GetMapping
//    public void getPlayersSidelinedFavorites() {
//        sidelinedFetcher.fetchFavorites();
//    }
//
//    @GetMapping("/{leagueId}")
//    public void getPlayersSidelinedByLeagueId(@PathVariable Long leagueId) {
//        sidelinedFetcher.fetchByLeagueId(leagueId);
//    }
//
//    @GetMapping("/{leagueId}/{season}")
//    public void getPlayersSidelinedByLeagueId(@PathVariable Long leagueId, @PathVariable Integer season) {
//        sidelinedFetcher.fetchByLeagueAndSeason(leagueId, season);
//    }

}

