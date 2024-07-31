package com.footystars.foot8.business.controller;

import com.footystars.foot8.api.model.standings.standing.Standing;
import com.footystars.foot8.business.service.StandingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/standings")
public class StandingController {

    private final StandingsService standingsService;

    @GetMapping("/{leagueId}")
    public ResponseEntity<List<Standing>> getStandingByLeagueId(@PathVariable Long leagueId) {
        return ResponseEntity.ok(standingsService.getStandingsByLeagueId(leagueId));
    }

}
