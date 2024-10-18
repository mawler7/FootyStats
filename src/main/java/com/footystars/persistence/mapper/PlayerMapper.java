package com.footystars.persistence.mapper;

import com.footystars.model.api.Players;
import com.footystars.model.entity.Player;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
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

}