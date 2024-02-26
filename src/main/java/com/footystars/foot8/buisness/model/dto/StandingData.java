package com.footystars.foot8.buisness.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandingData {
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
