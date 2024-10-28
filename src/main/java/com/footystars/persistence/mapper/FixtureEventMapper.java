package com.footystars.persistence.mapper;

import com.footystars.model.dto.FixtureEventDto;
import com.footystars.model.entity.FixtureEvent;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FixtureEventMapper {
    FixtureEvent toEntity(FixtureEventDto fixtureEventDto);

    FixtureEventDto toDto(FixtureEvent fixtureEvent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FixtureEvent partialUpdate(FixtureEventDto fixtureEventDto, @MappingTarget FixtureEvent fixtureEvent);
}