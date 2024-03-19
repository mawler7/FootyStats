package com.footystars.foot8.persistence.entities.players.player;

import com.footystars.foot8.api.model.players.info.PlayerInfo;
import com.footystars.foot8.persistence.entities.players.seaon.PlayerSeasonMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PlayerSeasonMapper.class})
public interface PlayerMapper {

    Player toEntity(PlayerDto playerDto);


    PlayerDto playerInfoToDto(PlayerInfo player);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Player partialUpdate(PlayerDto playerDto, @MappingTarget Player player);

    PlayerDto toDto(Player player);
}