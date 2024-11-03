package com.footystars.controller.business;

import com.footystars.model.dto.PlayerResponseDto;
import com.footystars.service.business.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/id/{playerId}")
    public ResponseEntity<PlayerResponseDto> getByPlayerId(@PathVariable Long playerId) {
        var player = playerService.getPlayerDetails(playerId);
        return ResponseEntity.ok(player);
    }


}
