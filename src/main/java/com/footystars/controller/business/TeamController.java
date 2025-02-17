package com.footystars.controller.business;

import com.footystars.model.dto.team.ClubDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<ClubDto> getClub(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getClubDto(id));
    }
}
