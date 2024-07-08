package com.footystars.foot8.business.controller;

import com.footystars.foot8.business.model.dto.FixtureDetailsDto;
import com.footystars.foot8.business.service.FixtureService;
import com.footystars.foot8.mapper.FixtureMapper;
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

    private static final Logger logger = LoggerFactory.getLogger(FixtureController.class);

    private final FixtureService fixtureService;
    private final FixtureMapper fixtureMapper;

//

    //
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

//    @GetMapping("/{leagueId}/{seasonYear}")
//    public void getFixturesByLeagueIdAndSeason(@PathVariable Long leagueId, @PathVariable Integer seasonYear) {
//        fixtureService.findBySeasonsLeagueIdAndSeasonsYear(leagueId, seasonYear);
//    }

    @GetMapping("/today")
    public void getTodayFixturesForSelectedLeagues() {
        fixtureService.findTodayFixtures();
    }


}
