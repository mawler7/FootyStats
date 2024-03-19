package com.footystars.foot8.persistence.entities.players.seaon;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerSeasonMapper {


    PlayerSeason toEntity(PlayerSeasonDto playerSeasonDto);


    PlayerSeasonDto toDto(PlayerSeason playerSeason);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PlayerSeason partialUpdate(PlayerSeasonDto playerSeasonDto, @MappingTarget PlayerSeason playerSeason);
}