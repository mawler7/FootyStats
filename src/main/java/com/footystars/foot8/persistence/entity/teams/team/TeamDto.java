package com.footystars.foot8.persistence.entity.teams.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.persistence.entity.club.ClubDto;
import com.footystars.foot8.persistence.entity.coachs.CoachDto;
import com.footystars.foot8.persistence.entity.players.player.PlayerDto;
import com.footystars.foot8.persistence.entity.teams.statistics.TeamStatsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto implements Serializable {
   private Long id;
   private ClubDto club;
   private CoachDto coach;
   private Set<PlayerDto> players;
   private Set<TeamStatsDto> teamStats;
}