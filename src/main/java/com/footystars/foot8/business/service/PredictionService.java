package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.predictions.response.PredictionApi;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.business.service.fixture.FixtureService;
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
                    var newPrediction  = predictionMapper.toEntity(predictionDto);
                    newPrediction.setFixturesH2H(h2h);

                if (fixtureEntity.getPrediction() == null) {
                    var savedPrediction = predictionRepository.save(newPrediction);
                    fixtureEntity.setPrediction(savedPrediction);
                    fixtureService.save(fixtureEntity);
                    log.info("Predictions saved for fixture id {}", fixtureId);
                } else {
                    var existingPrediction = fixtureEntity.getPrediction();
                    if (!existingPrediction.equals(newPrediction)) {
                        newPrediction.setId(existingPrediction.getId());
                        var savedPrediction = predictionRepository.save(newPrediction);
                        fixtureEntity.setPrediction(savedPrediction);
                        fixtureService.save(fixtureEntity);
                        log.info("Predictions updated for fixture id {}", fixtureId);
                    } else {
                        log.info("Predictions are identical, no update required for fixture id {}", fixtureId);
                    }
                }
            }
        } catch (Exception e) {
           log.error(e.getMessage(), e);
        }
    }
}
