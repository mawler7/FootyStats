package com.footystars.model.dto.fixture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DBViewMatchDto implements Serializable {
    private Long id;
    private ZonedDateTime date;
    private String homeTeamName;
    private String homeTeamLogo;
    private String awayTeamName;
    private String awayTeamLogo;
    private Long leagueId;
    private String leagueName;
    private String leagueLogo;
    private String status;
    private Integer home;
    private Integer away;
    private Integer halfTimeHome;
    private Integer fullTimeHome;
    private Integer extraTimeHome;
    private Integer penaltiesHome;
    private Integer halfTimeAway;
    private Integer fullTimeAway;
    private Integer extraTimeAway;
    private Integer penaltiesAway;
}
