package com.footystars.controller.business;

import com.footystars.model.api.Standings;
import com.footystars.service.business.StandingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing league standings retrieval.
 * Provides an endpoint to fetch standings for a specific league.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/standing")
@CrossOrigin(origins = "http://localhost:3000")
public class StandingController {

    private final StandingsService standingsService;

    /**
     * Retrieves standings for a given league ID.
     *
     * @param leagueId the ID of the league.
     * @return a list of {@link Standings.StandingApi.StandingLeague.Standing} objects representing league standings.
     */
    @GetMapping("/{leagueId}")
    public ResponseEntity<List<Standings.StandingApi.StandingLeague.Standing>> getStandingsByLeagueId(@PathVariable Long leagueId) {
        var standings = standingsService.findByLeagueId(leagueId);
        return ResponseEntity.ok(standings);
    }
}
