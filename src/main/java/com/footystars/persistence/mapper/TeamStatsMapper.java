package com.footystars.persistence.mapper;

import com.footystars.model.api.TeamStatistics;
import com.footystars.model.entity.TeamStats;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamStatsMapper {

    TeamStats toEntity(TeamStatistics.TeamStatsApi teamStatistic);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TeamStats partialUpdate(TeamStatistics.TeamStatsApi teamStatistic, @MappingTarget TeamStats teamStats);

    TeamStats toEntity(TeamStats teamStats);


}