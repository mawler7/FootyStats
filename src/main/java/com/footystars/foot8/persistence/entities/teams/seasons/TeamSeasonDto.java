package com.footystars.foot8.persistence.entities.teams.seasons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.persistence.entities.coachs.coach.CoachDto;
import com.footystars.foot8.persistence.entities.players.seaon.PlayerSeasonDto;
import com.footystars.foot8.persistence.entities.teams.statistics.TeamStatsDto;
import com.footystars.foot8.persistence.entities.teams.team.TeamDto;
import jakarta.persistence.Embeddable;
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
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamSeasonDto implements Serializable {

   private Long id;
   private TeamDto team;
   private Set<PlayerSeasonDto> playerSeasons;
   private Set<CoachDto> coachs;
   private TeamStatsDto teamStats;


}