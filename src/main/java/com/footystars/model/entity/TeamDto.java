package com.footystars.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.api.Players;
import com.footystars.model.api.TeamsInfo;
import com.footystars.model.dto.LeagueDto;
import com.footystars.model.dto.MatchDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto implements Serializable {
   private Long id;
   private TeamsInfo.TeamInfo info;
   private TeamsInfo.VenueDto venue;
   private Coach coach;
   private LeagueDto league;
   private TeamStats statistics;
   private List<Players.PlayerDto> players;
   private List<MatchDto> matches;
}