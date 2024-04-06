package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.predictions.response.PredictionApi;
import com.footystars.foot8.buisness.model.entity.Fixture;
import com.footystars.foot8.exception.PredictionFetchException;
import com.footystars.foot8.mapper.PredictionMapper;
import com.footystars.foot8.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final FixtureService fixtureService;
    private final PredictionMapper predictionMapper;
    private final PredictionRepository predictionRepository;

    private final Logger log = LoggerFactory.getLogger(PredictionService.class);
    public void fetchPredictions(@NotNull PredictionApi predictionApi, @NotNull Long fixtureId) {
        try {
            var optionalFixture = fixtureService.findById(fixtureId);
            if (optionalFixture.isPresent()) {
                var fixtureEntity = optionalFixture.get();
                if (fixtureEntity.getPrediction() == null) {
                    var h2h = new HashSet<Fixture>();
                    predictionApi.getH2h().forEach(f -> {
                        var id = f.getFixture().getId();
                        var optionalFixtureH2h = fixtureService.findById(id);
                        if (optionalFixtureH2h.isPresent()) {
                            var fixture = optionalFixtureH2h.get();
                            h2h.add(fixture);
                        }
                    });
                    var predictionDto = predictionMapper.apiToDto(predictionApi);
                    var prediction = predictionMapper.toEntity(predictionDto);
                    prediction.setFixturesH2H(h2h);
                    var savedPrediction = predictionRepository.save(prediction);
                    fixtureEntity.setPrediction(savedPrediction);
                    fixtureService.save(fixtureEntity);
                } else {
                    log.info("Prediction already exists for fixtureId {}", fixtureId);
                }
            }
        } catch (Exception e) {
            throw new PredictionFetchException("Could not fetch prediction");
        }
    }

}
