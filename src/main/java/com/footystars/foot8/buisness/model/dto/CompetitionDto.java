package com.footystars.foot8.buisness.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.standings.standing.Standing;
import com.footystars.foot8.buisness.model.entity.Season;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompetitionDto implements Serializable {
    private Long id;
    private List<FixtureDto> fixtures;
    private List<TeamStatsDto> teamStatistics;
    private List<PlayerStatsDto> playerStatistics;
    private Season season;
    private LeagueDto league;
    private List<Standing> standings;
    private List<ClubDto> teams;
    private List<PlayerDto> players;
}