package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.lineups.lineup.LineupApi;
import com.footystars.foot8.business.model.entity.Lineup;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class, CoachMapper.class})
public interface LineupMapper {

    Lineup toEntity(LineupApi dto);

    Lineup toEntity(Lineup lineup);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Lineup partialUpdate(LineupApi lineupApi, @MappingTarget Lineup lineup);
}