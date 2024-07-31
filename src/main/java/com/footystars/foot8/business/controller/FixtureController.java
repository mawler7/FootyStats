package com.footystars.foot8.business.controller;

import com.footystars.foot8.business.model.dto.FixtureDetailsDto;
import com.footystars.foot8.business.model.dto.FixturePredictionDto;
import com.footystars.foot8.business.service.fixture.FixtureService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fixtures")
public class FixtureController {

    private final FixtureService fixtureService;

    private static final Logger logger = LoggerFactory.getLogger(FixtureController.class);

    @GetMapping("/{leagueId}/{year}")
    public ResponseEntity<List<FixtureDetailsDto>> getFixturesByLeagueAndSeason(@PathVariable String leagueId, @PathVariable Integer year) {
        var list = fixtureService.findBySeasonsLeagueIdAndSeasonsYear(Long.valueOf(leagueId), year);
        logger.info("Fetched teams info by league and season: {} {}", leagueId, year);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{fixtureId}")
    public ResponseEntity<FixtureDetailsDto> getFixturesByLeagueId(@PathVariable Long fixtureId) {
        return ResponseEntity.ok(fixtureService.getFixtureDetailsByFixtureId(fixtureId));
    }

    @GetMapping("/predictions/today")
    public ResponseEntity<List<FixturePredictionDto>> getTodayFixturesPredictions() {
        return ResponseEntity.ok(fixtureService.findTodayFixturesPredictions());
    }

    @GetMapping("/predictions/{date}")
    public ResponseEntity<List<FixturePredictionDto>> getFixturesPredictionsByDate(@PathVariable String date) {
        return ResponseEntity.ok(fixtureService.findFixturesPredictionsByDate(date));
    }

    @GetMapping("/byDate/{date}")
    public ResponseEntity<List<FixtureDetailsDto>> getFixturesByDate(@PathVariable String date) {
        return ResponseEntity.ok(fixtureService.findByDate(date));
    }

}
