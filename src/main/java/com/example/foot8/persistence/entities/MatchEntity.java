package com.example.foot8.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fixtureId;
    private String referee;
    private String timezone;
    private String date;
    private Long timestamp;
    private Long venueId;
    private String venueName;
    private String venueCity;
    private String leagueName;
    private String leagueCountry;
    private String leagueLogo;
    private String leagueFlag;
    private Long leagueSeason;
    private String leagueRound;
    private Long homeTeamId;
    private String homeTeamName;
    private String homeTeamLogo;
    private Boolean homeTeamWinner;
    private Long awayTeamId;
    private String awayTeamName;
    private String awayTeamLogo;
    private Boolean awayTeamWinner;
    private Integer homeTeamGoals;
    private Integer awayTeamGoals;
    private Integer halftimeHomeTeamGoals;
    private Integer halftimeAwayTeamGoals;
    private Integer extratimeHomeTeamGoals;
    private Integer extratimeAwayTeamGoals;
    private Integer penaltyHomeTeamGoals;
    private Integer penaltyAwayTeamGoals;

}

