package com.footystars.controller.business;

import com.footystars.model.dto.H2HDto;
import com.footystars.model.dto.MatchDetailsDto;
import com.footystars.model.dto.MatchDto;
import com.footystars.service.business.FixtureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fixture")
public class FixtureController {

    private final FixtureService fixtureService;

    @GetMapping("/{date}")
    public ResponseEntity<List<MatchDto>> getFixturesByDate(@PathVariable String date) {
        var todayFixtures = fixtureService.getMatchesByDate(date);
        return ResponseEntity.ok(todayFixtures);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MatchDetailsDto> getFixtureById(@PathVariable Long id) {
        var todayFixtures = fixtureService.getFixtureDtoByFixtureId(id);
        return ResponseEntity.ok(todayFixtures);
    }

    @GetMapping("/upcoming/{leagueId}")
    public ResponseEntity<List<MatchDto>> getCurrentSeasonFixturesByLeagueId(@PathVariable Long leagueId) {
        var todayFixtures = fixtureService.findCurrentSeasonFixturesByLeagueIdNotStarted(leagueId);
        return ResponseEntity.ok(todayFixtures);
    }

    @GetMapping("/current/{leagueId}")
    public ResponseEntity<List<MatchDto>> getCurrentSeasonFixturesByLeagueIdEnded(@PathVariable Long leagueId) {
        var todayFixtures = fixtureService.findCurrentSeasonFixturesByLeagueIdEnded(leagueId);
        return ResponseEntity.ok(todayFixtures);
    }

    @GetMapping("/h2h/{homeId}/{awayId}")
    public ResponseEntity<H2HDto> getHeadToHeadMatches(@PathVariable Long homeId, @PathVariable Long awayId) {
        var h2hMatches = fixtureService.getHeadToHeadMatches(homeId, awayId);
        return ResponseEntity.ok(h2hMatches);
    }


    @GetMapping("/team/id/{id}")
    public ResponseEntity<List<MatchDto>> getCurrentSeasonFixturesByClubId(@PathVariable Long id) {
        var todayFixtures = fixtureService.findCurrentSeasonFixturesByClubId(id);
        return ResponseEntity.ok(todayFixtures);
    }


}


