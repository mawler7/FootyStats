package com.footystars.controller.business;
import com.footystars.model.dto.LeagueDto;
import com.footystars.service.business.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leagues")
@CrossOrigin(origins = "http://localhost:3000")
public class LeagueController {

    private final LeagueService leagueService;

    @GetMapping("/current")
    public ResponseEntity<List<LeagueDto>> getAllLeagues() {
        var currentSeasonLeagues = leagueService.findCurrentSeasonLeagues();
        return ResponseEntity.ok(currentSeasonLeagues);
    }

    @GetMapping("/{leagueId}")
    public ResponseEntity<LeagueDto> getLeagueSeasonsByLeagueId(@PathVariable Long leagueId) {
        var leagueSeasons = leagueService.findLeagueSeasonsByLeagueId(leagueId);
        return ResponseEntity.ok(leagueSeasons);
    }

}
