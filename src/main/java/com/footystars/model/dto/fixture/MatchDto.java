package com.footystars.model.dto.fixture;

import com.footystars.model.dto.bet.BetDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class MatchDto implements Serializable {
    private Long id;
    private ZonedDateTime date;
    private Long homeTeamId;
    private String homeTeamName;
    private String homeTeamLogo;
    private Long awayTeamId;
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
    private Boolean isAdvice;
    private Boolean isAwayGoals;
    private Boolean isHomeGoals;
    private Boolean isOverUnder;
    private List<BetDto> bets = new ArrayList<>();
    private List<ClubMatchDto> homeForm = new ArrayList<>();
    private List<ClubMatchDto> awayForm = new ArrayList<>();

    public MatchDto(Long id, ZonedDateTime date, Long homeTeamId, String homeTeamName, String homeTeamLogo,
                    Long awayTeamId, String awayTeamName, String awayTeamLogo, String leagueName, String leagueLogo,
                    Long leagueId, Integer season, String round, String elapsed, String status, Integer home,
                    Integer away, String homePrediction, String awayPrediction, String underOver, String advice,
                    Integer halfTimeHome, Integer halfTimeAway, Integer fullTimeHome, Integer fullTimeAway,
                    Integer extraTimeHome, Integer extraTimeAway, Integer penaltiesHome, Integer penaltiesAway,
                    Boolean isAdvice, Boolean isAwayGoals, Boolean isHomeGoals, Boolean isOverUnder) {
        this.id = id;
        this.date = date;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.homeTeamLogo = homeTeamLogo;
        this.awayTeamId = awayTeamId;
        this.awayTeamName = awayTeamName;
        this.awayTeamLogo = awayTeamLogo;
        this.leagueName = leagueName;
        this.leagueLogo = leagueLogo;
        this.leagueId = leagueId;
        this.season = season;
        this.round = round;
        this.elapsed = elapsed;
        this.status = status;
        this.home = home;
        this.away = away;
        this.homePrediction = homePrediction;
        this.awayPrediction = awayPrediction;
        this.underOver = underOver;
        this.advice = advice;
        this.halfTimeHome = halfTimeHome;
        this.halfTimeAway = halfTimeAway;
        this.fullTimeHome = fullTimeHome;
        this.fullTimeAway = fullTimeAway;
        this.extraTimeHome = extraTimeHome;
        this.extraTimeAway = extraTimeAway;
        this.penaltiesHome = penaltiesHome;
        this.penaltiesAway = penaltiesAway;
        this.isAdvice = isAdvice;
        this.isAwayGoals = isAwayGoals;
        this.isHomeGoals = isHomeGoals;
        this.isOverUnder = isOverUnder;
    }
}
