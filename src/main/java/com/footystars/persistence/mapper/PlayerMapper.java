package com.footystars.persistence.mapper;

import com.footystars.model.api.Players;
import com.footystars.model.dto.player.PlayerCareerDto;
import com.footystars.model.entity.Player;
import com.footystars.model.dto.player.PlayerDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {

    Player toEntity(Players.PlayerDto playerDto);

    Players.PlayerDto toDto(Player player);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Player partialUpdate(Players.PlayerDto playerDto, @MappingTarget Player player);

    default Players.PlayerStats map(Set<Players.PlayerStats> statsSet) {
        return statsSet != null && !statsSet.isEmpty() ? statsSet.iterator().next() : null;
    }

    default Players.PlayerStats map(List<Players.PlayerStats> statsList) {
        return statsList != null && !statsList.isEmpty() ? statsList.get(0) : null;
    }

    default List<Players.PlayerStats> mapToList(Players.PlayerStats stats) {
        return stats != null ? List.of(stats) : List.of();
    }

    @Mapping(target = "season", source = "statistics.league.season")
    @Mapping(target = "clubName", source = "statistics.club.clubName")
    @Mapping(target = "clubLogo", source = "statistics.club.clubLogo")
    @Mapping(target = "leagueName", source = "statistics.league.leagueName")
    @Mapping(target = "leagueLogo", source = "statistics.league.logo")
    @Mapping(target = "form", source = "statistics.games.rating")
    @Mapping(target = "appearances", source = "statistics.games.appearances")
    @Mapping(target = "goals", source = "statistics.goals.goalsTotal")
    @Mapping(target = "assists", source = "statistics.goals.assists")
    @Mapping(target = "yellowCards", source = "statistics.cards.yellow")
    @Mapping(target = "redCards", source = "statistics.cards.red")
    PlayerCareerDto toPlayerCareerDto(Player player);

    PlayerDto toPlayerDto(Player player);

}