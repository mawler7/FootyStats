package com.footystars.controller.business;


import com.footystars.service.business.FixtureService;
import com.footystars.service.business.PredictionCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/prediction")
public class PredictionController {


    private final PredictionCheckerService predictionCheckerService;
    private final FixtureService fixtureService;

        @GetMapping("/check")
    public void checkPredictions() {
            var uncheckedPredictions = fixtureService.findUncheckedPredictions();
            predictionCheckerService.updatePredictionResult(uncheckedPredictions);

    }

}
