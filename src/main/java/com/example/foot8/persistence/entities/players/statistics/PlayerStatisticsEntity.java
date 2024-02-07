package com.example.foot8.persistence.entities.players.statistics;

import com.example.foot8.api.players.model.statistics.Cards;
import com.example.foot8.api.players.model.statistics.Dribbles;
import com.example.foot8.api.players.model.statistics.Duels;
import com.example.foot8.api.players.model.statistics.Fouls;
import com.example.foot8.api.players.model.statistics.Games;
import com.example.foot8.api.players.model.statistics.Goals;
import com.example.foot8.api.players.model.statistics.Passes;
import com.example.foot8.api.players.model.statistics.Penalty;
import com.example.foot8.api.players.model.statistics.Shots;
import com.example.foot8.api.players.model.statistics.Substitutes;
import com.example.foot8.api.players.model.statistics.Tackles;
import com.example.foot8.api.teams.statistics.model.league.League;
import com.example.foot8.api.teams.statistics.model.team.Team;
import com.example.foot8.persistence.entities.players.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "player_statistics")
public class PlayerStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @Embedded
    private Team team;

    @Embedded
    private League league;

    @Embedded
    private Games games;

    @Embedded
    private Substitutes substitutes;

    @Embedded
    @AttributeOverride(name = "total", column = @Column(name = "shots_total"))
    private Shots shots;

    @Embedded
    @AttributeOverride(name = "total", column = @Column(name = "goals_total"))
    private Goals goals;

    @Embedded
    @AttributeOverride(name = "total", column = @Column(name = "passes_total"))
    private Passes passes;

    @Embedded
    @AttributeOverride(name = "total", column = @Column(name = "tackles_total"))
    private Tackles tackles;

    @Embedded
    @AttributeOverride(name = "total", column = @Column(name = "duels_total"))
    @AttributeOverride(name = "won", column = @Column(name = "duels_won"))
    private Duels duels;

    @Embedded
    private Dribbles dribbles;

    @Embedded
    private Fouls fouls;

    @Embedded
    private Cards cards;

    @Embedded
    @AttributeOverride(name = "won", column = @Column(name = "penalties_won"))
    private Penalty penalty;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = LocalDateTime.now();
    }

}