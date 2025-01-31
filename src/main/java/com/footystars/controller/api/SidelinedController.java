package com.footystars.controller.api;

import com.footystars.service.api.SidelinedFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing data related to sidelined players.
 * Provides an endpoint to fetch information about players who are currently sidelined due to injuries or other reasons.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sidelined")
@CrossOrigin(origins = "http://localhost:3000")
public class SidelinedController {

    private final SidelinedFetcher sidelinedFetcher;

    /**
     * Fetches information about players who are currently sidelined.
     */
    @GetMapping
    public void getSidelinedPlayers() {
        sidelinedFetcher.fetchSidelinedPlayers();
    }
}
