package com.footystars.model.dto.team;

import com.footystars.model.api.TeamsInfo;
import com.footystars.model.dto.league.LeagueDto;
import com.footystars.model.entity.TeamStats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDetailsDto implements Serializable {
   private TeamsInfo.TeamInfo info;
   private LeagueDto league;
   private TeamStats statistics;
}