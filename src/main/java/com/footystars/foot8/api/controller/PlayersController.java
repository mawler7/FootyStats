package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.PlayersFetcher;
import com.footystars.foot8.api.service.fetcher.TrophiesFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayersController {

    private final PlayersFetcher playersFetcher;

    private final TrophiesFetcher trophiesFetcher;
    private static final Logger logger = LoggerFactory.getLogger(PlayersController.class);

    @GetMapping("/{leagueId}/{seasonYear}")
    public void getPlayersByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        try {
            playersFetcher.fetchPlayersByLeagueAndYear(leagueId, seasonYear);
        } catch (Exception e) {
            logger.error("Error while fetching players by league ID {} and season {}", leagueId, seasonYear, e);
        }
    }

    @GetMapping("/{leagueId}")
    public void getPlayersByLeagueId(@PathVariable Long leagueId) {
        try {
            playersFetcher.fetchPlayersByLeagueId(leagueId);
        } catch (Exception e) {
            logger.error("Error while fetching players by league ID {}", leagueId, e);
        }
    }



    @GetMapping("/trophies/{leagueId}")
    public void getPlayerTrophies(@PathVariable Long leagueId) {
//        trophiesFetcher.fetchByLeagueId(leagueId);
        logger.info("Fetched trophies for leagueId {}", leagueId);
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
