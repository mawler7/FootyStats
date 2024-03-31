package com.footystars.foot8.persistence.entity.fixtures.statistics;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FixtureStatMapper {
//    FixtureStat toEntity(FixtureStatDto fixtureStatDto);
//
//    FixtureStatDto toDto(FixtureStat fixtureStat);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    FixtureStat partialUpdate(FixtureStatDto fixtureStatDto, @MappingTarget FixtureStat fixtureStat);
}