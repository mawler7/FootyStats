package com.footystars.foot8.persistence.entity.teams.team;

import com.footystars.foot8.persistence.entity.club.ClubMapper;
import com.footystars.foot8.persistence.entity.competitions.CompetitionMapper;
import com.footystars.foot8.persistence.entity.players.player.PlayerMapper;
import com.footystars.foot8.persistence.entity.teams.statistics.TeamStatsMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClubMapper.class, PlayerMapper.class, CompetitionMapper.class, TeamStatsMapper.class})
public interface TeamMapper {
    Team toEntity(TeamDto teamDto);

    TeamDto toDto(Team team);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamDto teamDto, @MappingTarget Team team);


}