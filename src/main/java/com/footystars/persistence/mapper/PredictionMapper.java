package com.footystars.persistence.mapper;

import com.footystars.model.api.Predictions;
import com.footystars.model.entity.Prediction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FixtureMapper.class})
public interface PredictionMapper {

    Prediction toEntity(Predictions.PredictionDto predictionDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Prediction partialUpdate(Predictions.PredictionDto predictionDto, @MappingTarget Prediction prediction);

}