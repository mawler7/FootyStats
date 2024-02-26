package com.footystars.foot8.buisness.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StandingController {

//    private final StandingService standingService;
//    private final LeagueService leagueService;
//    @GetMapping("/standings-all")
//    public List<StandingDataWrapper> getStandingsForAllLeaguesInCurrentSeason(){
//        return standingService.getStandingsForAllLeagues();
//    }
//
//    @GetMapping("/standings/{id}")
//    public StandingDataWrapper getStandingsForLeagueInCurrentSeason(@PathVariable Long id){
//        Optional<LeagueEntity> league = leagueService.findById(id);
//        if(league.isEmpty()) {
//            throw new LeagueException("League not found with id: " + id);
//        }
//        return standingService.getLeagueStandingsById(id);
//    }
//
//    @GetMapping("/standings/{leagueId}")
//    public ResponseEntity<StandingDataWrapper> getLeagueStandings(@PathVariable Long leagueId) {
//        try{
//            StandingDataWrapper standings = standingService.getLeagueStandingsById(leagueId);
//            return ResponseEntity.ok(standings);
//        }catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
}
