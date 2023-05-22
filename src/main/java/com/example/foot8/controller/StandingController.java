package com.example.foot8.controller;

import com.example.foot8.buisness.standing.StandingDataWrapper;
import com.example.foot8.exception.LeagueException;
import com.example.foot8.persistence.entities.LeagueEntity;
import com.example.foot8.service.LeagueService;
import com.example.foot8.service.StandingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StandingController {

    private final StandingService standingService;
    private final LeagueService leagueService;
    @GetMapping("/standings-all")
    public List<StandingDataWrapper> getStandingsForAllLeaguesInCurrentSeason(){
        return standingService.getStandingsForAllLeagues();
    }

    @GetMapping("/standings/{id}")
    public StandingDataWrapper getStandingsForLeagueInCurrentSeason(@PathVariable Long id){
        Optional<LeagueEntity> league = leagueService.findById(id);
        if(league.isEmpty()) {
            throw new LeagueException("League not found with id: " + id);
        }
        return standingService.getStandingsForLeague(league.get().getName());
    }

}
