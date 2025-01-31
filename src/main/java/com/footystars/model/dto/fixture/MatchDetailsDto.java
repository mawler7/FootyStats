package com.footystars.model.dto.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.api.Predictions;
import com.footystars.model.dto.bet.BetDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDetailsDto implements Serializable {
    private Long id;
    private ZonedDateTime date;
    private String venueName;
    private String referee;
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
    private Integer halfTimeHome;
    private Integer halfTimeAway;
    private Integer fullTimeHome;
    private Integer fullTimeAway;
    private Integer extraTimeHome;
    private Integer extraTimeAway;
    private Integer penaltiesHome;
    private Integer penaltiesAway;


    private String homePrediction;
    private String awayPrediction;
    private String underOver;
    private String advice;
    private String winnerComment;
    private Boolean winOrDraw;
    private String homePercentage;
    private String awayPercentage;
    private String drawPercentage;


    private String homeForm;
    private String awayForm;
    private String homeAtt;
    private String awayAtt;
    private String homeDef;
    private String awayDef;
    private String homePoissonPercentage;
    private String awayPoissonPercentage;
    private String homeH2HPercentage;
    private String awayH2HPercentage;
    private String homeGoalsComparison;
    private String awayGoalsComparison;
    private String homeTotalComparison;
    private String awayTotalComparison;


    private List<BetDto> bets = new ArrayList<>();
    private Predictions.PredictionDto prediction;
    private List<LineupDto> lineups = new ArrayList<>();
    private List<FixturePlayerDto> players = new ArrayList<>();
    private List<FixtureStatisticDto> statistics = new ArrayList<>();
    private List<FixtureEventDto> events = new ArrayList<>();
}
