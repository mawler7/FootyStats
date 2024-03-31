package com.footystars.foot8.persistence.entity.players.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStatsDto implements Serializable {

    private Integer appearances;
    private Integer lineups;
    private Integer minutes;
    private Integer number;
    private String position;
    private Double rating;
    private Boolean captain;
    private Integer substitutedIn;
    private Integer substitutedOut;
    private Integer bench;
    private Integer shotsOnTarget;
    private Integer shots;
    private Integer goals;
    private Integer goalsConceded;
    private Integer assists;
    private Integer saves;
    private Integer passes;
    private Integer keyPasses;
    private Integer passesAccuracy;
    private Integer tackles;
    private Integer blocks;
    private Integer interceptions;
    private Integer duels;
    private Integer duelsWon;
    private Integer dribblesAttempts;
    private Integer dribblesSuccess;
    private Integer dribblesPast;
    private Integer foulsDrawn;
    private Integer foulsCommitted;
    private Integer yellowCards;
    private Integer yellowRedCards;
    private Integer redCards;
    private Integer penaltiesCommitted;
    private Integer penaltiesScored;
    private Integer penaltiesMissed;
    private Integer penaltiesSaved;
    private Integer penaltiesWon;
    private LocalDateTime lastUpdated;
}