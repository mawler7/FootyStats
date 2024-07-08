package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.SidelinedFetcher;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sidelined")
public class SidelinedController {

    private final SidelinedFetcher sidelinedFetcher;

    @GetMapping
    public void getPlayersSidelinedFavorites() {
        sidelinedFetcher.fetchFavorites();
    }

        @GetMapping("/{leagueId}")
        public void getPlayersSidelinedByLeagueId(@PathVariable Long leagueId)   {
            sidelinedFetcher.fetchByLeagueId(leagueId);
        }

    @GetMapping("/{leagueId}/{season}")
    public void getPlayersSidelinedByLeagueId(@PathVariable Long leagueId, @PathVariable Integer season)   {
        sidelinedFetcher.fetchByLeagueAndSeason(leagueId, season);
    }

    }

