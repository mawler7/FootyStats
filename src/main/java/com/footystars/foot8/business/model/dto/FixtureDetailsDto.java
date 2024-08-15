package com.footystars.foot8.business.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.events.Events;
import com.footystars.foot8.api.model.fixtures.events.event.FixtureEvent;
import com.footystars.foot8.api.model.fixtures.statistics.statistic.Statistic;
import com.footystars.foot8.business.model.entity.Prediction;
import com.footystars.foot8.business.model.entity.TeamStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureDetailsDto implements Serializable {
    private Long id;
    private String leagueName;
    private String round;
    private ZonedDateTime date;
    private String city;
    private String venueName;
    private String referee;

    private String status;
    private String fullStatus;
    private String elapsed;

    private Integer home;
    private Integer away;


    private Integer homePT;
    private Integer homeET;
    private Integer homeHT;
    private Integer homeFT;
    private Integer awayHT;
    private Integer awayFT;
    private Integer awayET;
    private Integer awayPT;

    private List<FixtureEvent> events;
    private List<Statistic> statistics;

    private SeasonDto season;
    private LeagueDto league;


    private List<LineupDto> lineups;
    private TeamDto homeTeam;
    private TeamDto awayTeam;


    private List<BetDto> bets;
    private PredictionDto prediction;


}