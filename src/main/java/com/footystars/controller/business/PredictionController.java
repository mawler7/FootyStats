package com.footystars.controller.business;

import com.footystars.service.business.PredictionCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing predictions verification.
 * Provides an endpoint to check and update prediction results.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/prediction")
public class PredictionController {

    private final PredictionCheckerService predictionCheckerService;

    /**
     * Verifies and updates predictions that have not yet been checked.
     * It retrieves unchecked predictions and processes them to update their results.
     */
    @GetMapping("/verify")
    public void checkPredictions() {
        predictionCheckerService.updatePredictionResult();
    }


}
