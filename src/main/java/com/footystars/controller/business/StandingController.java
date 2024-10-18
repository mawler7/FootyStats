package com.footystars.controller.business;

import com.footystars.model.api.Standings;
import com.footystars.service.business.StandingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/standing")
public class StandingController {

    private final StandingsService standingsService;

    @GetMapping("/{leagueId}")
    public ResponseEntity<List<Standings.StandingApi.StandingLeague.Standing>> getStandingsByLeagueId(@PathVariable Long leagueId) {
        var standings = standingsService.findByLeagueId(leagueId);
        return ResponseEntity.ok(standings);
    }

}
