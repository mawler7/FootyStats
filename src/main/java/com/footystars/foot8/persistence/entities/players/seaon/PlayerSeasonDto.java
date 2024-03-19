package com.footystars.foot8.persistence.entities.players.seaon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.persistence.entities.players.player.PlayerDto;
import com.footystars.foot8.persistence.entities.players.statistics.PlayerStatsDto;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeasonDto;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerSeasonDto implements Serializable {
   private Long id;
   private PlayerDto player;
   private PlayerStatsDto playerStats;
}