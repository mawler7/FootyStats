package com.footystars.persistence.mapper;

import com.footystars.model.entity.FixtureStatistic;
import com.footystars.model.dto.FixtureStatisticDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FixtureStatisticMapper {
    FixtureStatistic toEntity(FixtureStatisticDto fixtureStatisticDto);

    FixtureStatisticDto toDto(FixtureStatistic fixtureStatistic);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FixtureStatistic partialUpdate(FixtureStatisticDto fixtureStatisticDto, @MappingTarget FixtureStatistic fixtureStatistic);
}