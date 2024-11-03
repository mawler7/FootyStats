package com.footystars.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDto implements Serializable {

    private Long id;
    private ZonedDateTime date;
    private String homeTeamId;
    private String homeTeamName;
    private String homeTeamLogo;
    private String awayTeamId;
    private String awayTeamName;
    private String awayTeamLogo;
    private String leagueName;
    private String leagueLogo;
    private Long leagueId;
    private Integer season;
    private String round;
    private String elapsed;
    private String status;
    private Integer home;
    private Integer away;
    private String homePrediction;
    private String awayPrediction;
    private String underOver;
    private String advice;
    private Integer halfTimeHome;
    private Integer halfTimeAway;
    private Integer fullTimeHome;
    private Integer fullTimeAway;
    private Integer extraTimeHome;
    private Integer extraTimeAway;
    private Integer penaltiesHome;
    private Integer penaltiesAway;

    private Double averageHomeOdd;
    private Double averageDrawOdd;
    private Double averageAwayOdd;

}
