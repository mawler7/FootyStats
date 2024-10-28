package com.footystars.controller.business;

import com.footystars.model.entity.TeamDto;
import com.footystars.service.business.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<List<TeamDto>> getFixtureById(@PathVariable Long id) {
        var todayFixtures = teamService.getTeamSeasonsByClubId(id);
        return ResponseEntity.ok(todayFixtures);
    }

}
