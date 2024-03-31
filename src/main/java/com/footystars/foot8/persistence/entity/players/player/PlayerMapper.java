package com.footystars.foot8.persistence.entity.players.player;

import com.footystars.foot8.api.model.players.info.PlayerInfo;
import com.footystars.foot8.persistence.entity.competitions.CompetitionMapper;
import com.footystars.foot8.persistence.entity.players.statistics.PlayerStatsMapper;
import com.footystars.foot8.persistence.entity.teams.team.TeamMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
uses = {PlayerStatsMapper.class, CompetitionMapper.class, TeamMapper.class })
public interface PlayerMapper {

    Player toEntity(PlayerDto playerDto);


    @Mapping(source = "playerId", target = "id")
    PlayerDto playerInfoToDto(PlayerInfo player);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Player partialUpdate(PlayerDto playerDto, @MappingTarget Player player);

    PlayerDto toDto(Player player);
}