package com.footystars.controller.business;

import com.footystars.model.dto.team.TeamDto;
import com.footystars.service.business.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing team-related data retrieval.
 * Provides an endpoint to fetch a team's seasons by its ID.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {

    private final TeamService teamService;

    /**
     * Retrieves team seasons for a given club ID.
     *
     * @param id the ID of the club.
     * @return a list of {@link TeamDto} representing the club's seasons.
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<TeamDto>> getTeamById(@PathVariable Long id) {
        var teamSeasons = teamService.getTeamSeasonsByClubId(id);
        return ResponseEntity.ok(teamSeasons);
    }
}
