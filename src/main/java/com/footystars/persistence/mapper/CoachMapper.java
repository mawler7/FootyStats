package com.footystars.persistence.mapper;

import com.footystars.model.api.Coaches;
import com.footystars.model.entity.Coach;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TeamMapper.class})
public interface CoachMapper {

    Coach toEntity(Coaches.CoachDto coachDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Coach partialUpdate(Coaches.CoachDto coachDto, @MappingTarget Coach coach);
}