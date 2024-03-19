package com.footystars.foot8.persistence.entities.players.statistics;

import com.footystars.foot8.persistence.entities.players.seaon.PlayerSeason;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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


    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = LocalDateTime.now();
    }

}