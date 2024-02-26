package com.footystars.foot8.buisness.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandingLeagueDto {
    private Long leagueId;
    private String leagueName;
    private String leagueLogo;
    private Long teamId;
    private String teamName;
    private int position;
    private String teamLogo;
    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int points;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private String form;
}
