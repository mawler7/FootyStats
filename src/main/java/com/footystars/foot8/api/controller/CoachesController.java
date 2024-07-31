package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.CoachesFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.footystars.foot8.utils.LogsNames.COACHES_FETCHED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coaches")
public class CoachesController {

    private final CoachesFetcher coachesFetcher;
    private static final Logger logger = LoggerFactory.getLogger(CoachesController.class);

    @GetMapping("/{leagueId}")
    public void getCoachesForSelectedTeams(@PathVariable Long leagueId) {
        coachesFetcher.fetchCoachesByLeagueId(leagueId);
        logger.info("Fetched coaches for leagueId {}", leagueId);
    }

    @GetMapping
    public void getCoaches() {
        coachesFetcher.fetchTopLeaguesCoaches();
        logger.info(COACHES_FETCHED);
    }

}
