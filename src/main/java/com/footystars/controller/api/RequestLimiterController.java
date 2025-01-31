package com.footystars.controller.api;

import com.footystars.service.requester.RequestsLimiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing API request limits.
 * Provides an endpoint to retrieve the remaining request quota.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RequestLimiterController {

    private final RequestsLimiterService requestsLimiterService;

    /**
     * Retrieves the remaining request limit.
     *
     * @return the number of remaining requests allowed.
     */
    @GetMapping("/req")
    public Integer getLimit() {
        return requestsLimiterService.getRemaining();
    }
}
