package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.predictions.response.PredictionApi;
import com.footystars.foot8.business.model.dto.PredictionDto;
import com.footystars.foot8.business.model.entity.Prediction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {FixtureMapper.class})
public interface PredictionMapper {
    Prediction toEntity(PredictionDto predictionDto);

    PredictionDto toDto(Prediction prediction);


    PredictionDto apiToDto(PredictionApi predictionDetails);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Prediction partialUpdate(PredictionDto predictionDto, @MappingTarget Prediction prediction);
}