package com.example.foot8.controller;

import com.example.foot8.persistence.entities.MatchEntity;
import com.example.foot8.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FixtureController {

    private final MatchService matchService;

    @GetMapping("/fixtures/today")
    public Map<String, List<MatchEntity>> getTodayFixtures(){
        return matchService.findByTodayDate();
    }

    @GetMapping("/fixtures/h2h/{homeTeamId}/{awayTeamId}")
    public List<MatchEntity> getH2H(@PathVariable Long homeTeamId, @PathVariable Long awayTeamId) {
        return matchService.findHeadToHead(homeTeamId, awayTeamId);
    }

}
