package com.footystars.foot8.persistence.entity.fixtures.events;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FixtureEventMapper {
//    FixtureEvent toEntity(FixtureEvent fixtureEvent);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    FixtureEvent partialUpdate(FixtureEventDto fixtureEventDto, @MappingTarget FixtureEvent fixtureEvent);
}