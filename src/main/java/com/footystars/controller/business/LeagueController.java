package com.footystars.controller.business;

import com.footystars.model.dto.league.LeagueDetailsDto;
import com.footystars.model.dto.league.LeagueDto;
import com.footystars.service.business.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing league-related data retrieval.
 * Provides endpoints to fetch leagues for the current season and retrieve league details by ID.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/leagues")
@CrossOrigin(origins = "http://localhost:3000")
public class LeagueController {

    private final LeagueService leagueService;

    /**
     * Retrieves all leagues for the current season.
     *
     * @return a list of {@link LeagueDto} representing the leagues for the current season.
     */
    @GetMapping("/current")
    public ResponseEntity<List<LeagueDto>> getAllLeagues() {
        var currentSeasonLeagues = leagueService.findCurrentSeasonLeagues();
        return ResponseEntity.ok(currentSeasonLeagues);
    }

    /**
     * Retrieves details for a specific league, including its seasons.
     *
     * @param leagueId the ID of the league.
     * @return a {@link LeagueDetailsDto} containing details about the league and its seasons.
     */
    @GetMapping("/{leagueId}")
    public ResponseEntity<LeagueDetailsDto> getLeagueSeasonsByLeagueId(@PathVariable Long leagueId) {
        var leagueSeasons = leagueService.findLeagueSeasonsByLeagueId(leagueId);
        return ResponseEntity.ok(leagueSeasons);
    }
}
