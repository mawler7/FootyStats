package com.footystars.service.business;

import com.footystars.model.api.Predictions;
import com.footystars.model.entity.Prediction;
import com.footystars.persistence.mapper.PredictionMapper;
import com.footystars.persistence.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static com.footystars.utils.LogsNames.PREDICTIONS_ERROR;


@Service
@RequiredArgsConstructor
public class PredictionService {

    private final FixtureService fixtureService;
    private final PredictionMapper predictionMapper;
    private final PredictionRepository predictionRepository;
    private final Logger log = LoggerFactory.getLogger(PredictionService.class);

    @Transactional
    public void saveOrUpdatePrediction(@NotNull Predictions.PredictionDto predictionDto, @NotNull Long fixtureId) {
        try {
            fixtureService.findById(fixtureId).ifPresent(fixture -> {
                Prediction predictionEntity;
                if (fixture.getPrediction() == null) {
                    predictionEntity = predictionMapper.toEntity(predictionDto);
                    fixture.setPrediction(predictionRepository.save(predictionEntity));
                } else {
                    predictionEntity = fixture.getPrediction();
                    predictionMapper.partialUpdate(predictionDto, predictionEntity);
                    predictionEntity.setLastUpdated(ZonedDateTime.now());
                    fixture.setPrediction(predictionRepository.save(predictionEntity));
                }
                fixtureService.save(fixture);
            });
        } catch (Exception e) {
            log.error(PREDICTIONS_ERROR, fixtureId, e.getMessage(), e);
        }
    }


}
