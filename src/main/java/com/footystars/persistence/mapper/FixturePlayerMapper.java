package com.footystars.persistence.mapper;

import com.footystars.model.entity.FixturePlayer;
import com.footystars.model.dto.FixturePlayerDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FixturePlayerMapper {
    FixturePlayer toEntity(FixturePlayerDto fixturePlayerDto);

    FixturePlayerDto toDto(FixturePlayer fixturePlayer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FixturePlayer partialUpdate(FixturePlayerDto fixturePlayerDto, @MappingTarget FixturePlayer fixturePlayer);
}