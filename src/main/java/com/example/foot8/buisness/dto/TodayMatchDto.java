package com.example.foot8.buisness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodayMatchDto {
    private Long id;
    private Long leagueId;
    private String leagueName;
    private String leagueLogo;
    private String date;
    private String time;
    private String homeTeamLogo;
    private String homeTeamName;
    private Integer homeTeamGoals;
    private Integer awayTeamGoals;
    private String awayTeamLogo;
    private String awayTeamName;
    private Integer extratimeHomeTeamGoals;
    private Integer extratimeAwayTeamGoals;
    private Integer penaltyHomeTeamGoals;
    private Integer penaltyAwayTeamGoals;
}
