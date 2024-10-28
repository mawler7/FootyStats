package com.footystars.controller.api;

import com.footystars.service.api.SidelinedFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sidelined")
public class SidelinedController {

    private final SidelinedFetcher sidelinedFetcher;

    @GetMapping
    public void getSidelinedPlayers() {
        sidelinedFetcher.fetchSidelinedPlayers();
    }

}

