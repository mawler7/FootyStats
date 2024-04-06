package com.footystars.foot8.buisness.model.entity;

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

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "player_statistics")
public class PlayerStats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String position = "-";
    @Builder.Default
    private Boolean captain = false;
    @Builder.Default
    private Double rating = 0.0;
    @Builder.Default
    private Integer appearances = 0;
    @Builder.Default
    private Integer lineups = 0;
    @Builder.Default
    private Integer minutes = 0;
    @Builder.Default
    private Integer number = 0;
    @Builder.Default
    private Integer substitutedIn = 0;
    @Builder.Default
    private Integer substitutedOut = 0;
    @Builder.Default
    private Integer bench = 0;
    @Builder.Default
    private Integer shotsOnTarget = 0;
    @Builder.Default
    private Integer shots = 0;
    @Builder.Default
    private Integer goals = 0;
    @Builder.Default
    private Integer goalsConceded = 0;
    @Builder.Default
    private Integer assists = 0;
    @Builder.Default
    private Integer saves = 0;
    @Builder.Default
    private Integer passes = 0;
    @Builder.Default
    private Integer keyPasses = 0;
    @Builder.Default
    private Integer passesAccuracy = 0;
    @Builder.Default
    private Integer tackles = 0;
    @Builder.Default
    private Integer blocks = 0;
    @Builder.Default
    private Integer interceptions = 0;
    @Builder.Default
    private Integer duels = 0;
    @Builder.Default
    private Integer duelsWon = 0;
    @Builder.Default
    private Integer dribblesAttempts = 0;
    @Builder.Default
    private Integer dribblesSuccess = 0;
    @Builder.Default
    private Integer dribblesPast = 0;
    @Builder.Default
    private Integer foulsDrawn = 0;
    @Builder.Default
    private Integer foulsCommitted = 0;
    @Builder.Default
    private Integer yellowCards = 0;
    @Builder.Default
    private Integer yellowRedCards = 0;
    @Builder.Default
    private Integer redCards = 0;
    @Builder.Default
    private Integer penaltiesCommitted = 0;
    @Builder.Default
    private Integer penaltiesScored = 0;
    @Builder.Default
    private Integer penaltiesMissed = 0;
    @Builder.Default
    private Integer penaltiesSaved = 0;
    @Builder.Default
    private Integer penaltiesWon = 0;


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

}