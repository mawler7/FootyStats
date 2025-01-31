package com.footystars.model.dto.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.api.Players;
import com.footystars.model.api.TeamsInfo;
import com.footystars.model.dto.league.LeagueDto;
import com.footystars.model.dto.fixture.MatchDto;
import com.footystars.model.entity.Coach;
import com.footystars.model.entity.TeamStats;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto {
   private Long id;
   private TeamsInfo.TeamInfo info;
   private TeamsInfo.VenueDto venue;
   private Coach coach;
   private LeagueDto league;
   private TeamStats statistics;
   private List<Players.PlayerDto> players = new ArrayList<>();
   private List<MatchDto> matches = new ArrayList<>();
}
