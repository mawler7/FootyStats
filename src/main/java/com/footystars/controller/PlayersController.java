package com.footystars.controller;

import com.footystars.service.api.PlayersFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayersController {

    private final PlayersFetcher playersFetcher;

    @GetMapping("/{leagueId}/{seasonYear}")
    public void getPlayersByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        playersFetcher.fetchPlayersByLeagueAndYear(leagueId, seasonYear);
    }

    @GetMapping("/{leagueId}")
    public void getPlayersByLeagueId(@PathVariable Long leagueId) {
        playersFetcher.fetchPlayersByLeagueId(leagueId);
    }

    @GetMapping("/current")
    public void getCurrentSeasonPlayers() {
        playersFetcher.fetchCurrentSeasonTopLeaguesPlayers();
    }

    @GetMapping("/all")
    public void getAllPlayers() {
        playersFetcher.fetchByAllLeagues();
    }

}
