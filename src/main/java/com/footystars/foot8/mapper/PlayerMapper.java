package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.players.info.PlayerInfo;
import com.footystars.foot8.api.model.players.player.PlayerApi;
import com.footystars.foot8.business.model.dto.PlayerDto;
import com.footystars.foot8.business.model.entity.Player;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {

    Player toEntity(PlayerDto playerDto);

    @Mapping(source = "playerId", target = "id")
    PlayerDto playerInfoToDto(PlayerInfo player);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Player partialUpdate(PlayerDto playerDto, @MappingTarget Player player);

    PlayerDto toDto(Player player);


    @Mapping(source = "playerInfo.playerId", target = "id")
    @Mapping(source = "playerInfo.name", target = "name")
    @Mapping(source = "playerInfo.firstname", target = "firstname")
    @Mapping(source = "playerInfo.lastname", target = "lastname")
    @Mapping(source = "playerInfo.nationality", target = "nationality")
    @Mapping(source = "playerInfo.age", target = "age")
    @Mapping(source = "playerInfo.height", target = "height")
    @Mapping(source = "playerInfo.weight", target = "weight")
    @Mapping(source = "playerInfo.photo", target = "photo")
    @Mapping(source = "playerInfo.injured", target = "injured")
    @Mapping(source = "playerInfo.birth", target = "birth")
    PlayerDto playerApiToDto(PlayerApi playerApi);

}