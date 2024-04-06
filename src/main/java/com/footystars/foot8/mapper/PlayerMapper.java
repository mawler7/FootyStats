package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.lineups.lineup.player.LineupPlayer;
import com.footystars.foot8.api.model.players.info.PlayerInfo;
import com.footystars.foot8.buisness.model.dto.PlayerDto;
import com.footystars.foot8.buisness.model.entity.Player;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PlayerStatsMapper.class, CompetitionMapper.class, TeamMapper.class })
public interface PlayerMapper {

    Player toEntity(PlayerDto playerDto);

    @Mapping(source = "playerId", target = "id")
    PlayerDto playerInfoToDto(PlayerInfo player);



    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Player partialUpdate(PlayerDto playerDto, @MappingTarget Player player);

    PlayerDto toDto(Player player);

    List<Player> toEntityList(List<PlayerDto> playerDtos);

    Player lineupPlayerToEntity(LineupPlayer lineupPlayer);
}