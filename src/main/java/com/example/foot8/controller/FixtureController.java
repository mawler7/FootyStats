package com.example.foot8.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FixtureController {

//    private final FixtureService fixtureService;
//    @GetMapping("fixtures/byDate/today")
//    public ResponseEntity<Map<String, List<TodayMatchDto>>> getTodayMatchesByLeague() {
//        try {
//            Map<String, List<TodayMatchDto>> todayMatches = matchService.findByTodayDate();
//            return ResponseEntity.ok(todayMatches);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//
//    @GetMapping("fixtures/bySeason")
//    public ResponseEntity<Map<String, List<TodayMatchDto>>> getMatchesByCurrentSeason() {
//        try {
//            Map<String, List<TodayMatchDto>> currentSeason = matchService.findByCurrentSeason();
//            return ResponseEntity.ok(currentSeason);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//
//
//
//    @GetMapping("/fixtures/byDate/{dateString}")
//    public ResponseEntity<Map<String, List<TodayMatchDto>>> getMatchesByDate(@PathVariable String dateString) {
//        try {
//            Map<String, List<TodayMatchDto>> matches = matchService.findMatchesByDate(dateString);
//            return ResponseEntity.ok(matches);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/fixtures/h2h/{homeTeamId}/{awayTeamId}")
//    public List<MatchEntity> getH2H(@PathVariable Long homeTeamId, @PathVariable Long awayTeamId) {
//        return matchService.findHeadToHead(homeTeamId, awayTeamId);
//    }
//    @GetMapping("/wins")
//    public ResponseEntity<List<Map<String, Object>>> getWinsByResult(
//            @RequestParam("awayGoals") int awayGoals,
//            @RequestParam("homeGoals") int homeGoals) {
//        List<Map<String, Object>> result = matchService.getWinsByResult(awayGoals, homeGoals);
//        return ResponseEntity.ok(result);
//    }

}
