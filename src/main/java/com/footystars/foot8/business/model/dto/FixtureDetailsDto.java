package com.footystars.foot8.business.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.events.event.FixtureEvent;
import com.footystars.foot8.api.model.fixtures.statistics.statistic.Statistic;
import com.footystars.foot8.business.model.dto.BetDto;
import com.footystars.foot8.business.model.dto.PredictionDto;
import com.footystars.foot8.business.model.dto.SeasonDto;
import com.footystars.foot8.business.model.dto.TeamDto;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.business.model.entity.Lineup;
import com.footystars.foot8.business.model.entity.LineupDto;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
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
    private String date;
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
//    private int year;
//    private String startDate;
//    private String endDate;
//    private Boolean current;

    private List<LineupDto> lineups;
    private TeamDto homeTeam;
    private TeamDto awayTeam;


    private List<BetDto> bets;
    private PredictionDto prediction;
}