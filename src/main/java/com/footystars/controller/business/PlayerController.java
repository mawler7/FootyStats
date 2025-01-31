package com.footystars.controller.business;

import com.footystars.model.dto.player.PlayerResponseDto;
import com.footystars.model.dto.player.PlayerTopScorerDto;
import com.footystars.service.business.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing player-related data retrieval.
 * Provides endpoints to fetch player details and top scorers for a given league.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {

    private final PlayerService playerService;

    /**
     * Retrieves details for a specific player by their ID.
     *
     * @param playerId the ID of the player.
     * @return a {@link PlayerResponseDto} containing player details.
     */
    @GetMapping("/id/{playerId}")
    public ResponseEntity<PlayerResponseDto> getByPlayerId(@PathVariable Long playerId) {
        var player = playerService.getPlayerDetails(playerId);
        return ResponseEntity.ok(player);
    }

    /**
     * Retrieves the top scorers for a given league.
     *
     * @param leagueId the ID of the league for which top scorers should be retrieved.
     * @return a list of {@link PlayerTopScorerDto} representing the top scorers.
     */
    @GetMapping("/top-scorers")
    public ResponseEntity<List<PlayerTopScorerDto>> getTopScorers(@RequestParam int leagueId) {
        List<PlayerTopScorerDto> topScorers = playerService.getTopScorers(leagueId);
        return ResponseEntity.ok(topScorers);
    }
}
