package com.footystars.foot8.persistence.entities.teams.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.standings.standing.stat.goals.Goals;
import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeasonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamStandingDto implements Serializable {

    private Long id;

    private Integer rank;
    private Integer points;
    private Integer goalsDiff;
    private String groupInfo;
    private String form;
    private String status;
    private String description;
    private Integer totalPlayed;
    private Integer totalWin;
    private Integer totalDraw;
    private Integer totalLose;
    private Goals totalGoals;
    private Integer homeTeamPlayed;
    private Integer homeTeamWin;
    private Integer homeTeamDraw;
    private Integer homeTeamLose;
    private Goals homeTeamGoals;
    private Integer awayTeamPlayed;
    private Integer awayTeamWin;
    private Integer awayTeamDraw;
    private Integer awayTeamLose;
    private Goals awayTeamGoals;
    private Date update;

    private LeagueSeasonDto leagueSeason;

}