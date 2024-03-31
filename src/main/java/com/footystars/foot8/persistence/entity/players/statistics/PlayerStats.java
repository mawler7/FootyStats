package com.footystars.foot8.persistence.entity.players.statistics;

import com.footystars.foot8.persistence.entity.competitions.Competition;
import com.footystars.foot8.persistence.entity.players.player.Player;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "player_stats")
public class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;
    private Boolean captain;
    private Double rating;
    private Integer appearances;
    private Integer lineups;
    private Integer minutes;
    //always null
    private Integer number;
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


    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    public void setDefaults() {
        if (this.position == null) this.position = "-";
        if (this.captain == null) this.captain = false;
        if (this.appearances == null) this.appearances = 0;
        if (this.lineups == null) this.lineups = 0;
        if (this.minutes == null) this.minutes = 0;
        if (this.substitutedIn == null) this.substitutedIn = 0;
        if (this.substitutedOut == null) this.substitutedOut = 0;
        if (this.bench == null) this.bench = 0;
        if (this.shotsOnTarget == null) this.shotsOnTarget = 0;
        if (this.shots == null) this.shots = 0;
        if (this.goals == null) this.goals = 0;
        if (this.goalsConceded == null) this.goalsConceded = 0;
        if (this.assists == null) this.assists = 0;
        if (this.saves == null) this.saves = 0;
        if (this.passes == null) this.passes = 0;
        if (this.keyPasses == null) this.keyPasses = 0;
        if (this.passesAccuracy == null) this.passesAccuracy = 0;
        if (this.tackles == null) this.tackles = 0;
        if (this.blocks == null) this.blocks = 0;
        if (this.interceptions == null) this.interceptions = 0;
        if (this.duels == null) this.duels = 0;
        if (this.duelsWon == null) this.duelsWon = 0;
        if (this.dribblesAttempts == null) this.dribblesAttempts = 0;
        if (this.dribblesSuccess == null) this.dribblesSuccess = 0;
        if (this.dribblesPast == null) this.dribblesPast = 0;
        if (this.foulsDrawn == null) this.foulsDrawn = 0;
        if (this.foulsCommitted == null) this.foulsCommitted = 0;
        if (this.yellowCards == null) this.yellowCards = 0;
        if (this.yellowRedCards == null) this.yellowRedCards = 0;
        if (this.redCards == null) this.redCards = 0;
        if (this.penaltiesCommitted == null) this.penaltiesCommitted = 0;
        if (this.penaltiesScored == null) this.penaltiesScored = 0;
        if (this.penaltiesMissed == null) this.penaltiesMissed = 0;
        if (this.penaltiesSaved == null) this.penaltiesSaved = 0;
        if (this.penaltiesWon == null) this.penaltiesWon = 0;
        if (this.rating == null) this.rating = 0.0;


    }

}