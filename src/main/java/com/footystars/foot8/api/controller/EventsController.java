package com.footystars.foot8.api.controller;

import com.footystars.foot8.api.service.fetcher.EventsFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.footystars.foot8.utils.LogsNames.EVENTS_FETCHED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventsController {

    private final EventsFetcher eventsFetcher;

    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    @GetMapping("/{leagueId}/{seasonYear}")
    public void getEventsByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
        eventsFetcher.fetchEventsByLeagueIdAndSeason(leagueId, seasonYear);
        logger.info(EVENTS_FETCHED, leagueId, seasonYear);
    }
    @GetMapping("/{leagueId}")
    public void getEventsByLeagueId(@PathVariable Long leagueId) {
        eventsFetcher.fetchEventsForAllSeasonsByLeagueId(leagueId);
        logger.info("Fetched fixture events for leagueId {}", leagueId);
    }

    @GetMapping("/selected")
    public void getAllEventsForSelectedLeagues() {
        eventsFetcher.fetchEventsFroAllSelectedLeagues();
    }

    @GetMapping("/current")
    public void getCurrentSeasonEventsForSelectedLeagues() {
        eventsFetcher.fetchEventsForCurrentSeason();
    }


}
