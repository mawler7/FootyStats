package com.footystars.foot8.business.controller;

import com.footystars.foot8.business.model.dto.LeagueDto;
import com.footystars.foot8.business.service.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    @GetMapping("/all")
    public ResponseEntity<List<LeagueDto>> getAll() {
        return ResponseEntity.ok(leagueService.findAllLeaguesDto());
    }

}
