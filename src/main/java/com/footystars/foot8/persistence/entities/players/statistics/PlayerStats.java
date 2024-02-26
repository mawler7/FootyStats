package com.footystars.foot8.persistence.entities.players.statistics;

import com.footystars.foot8.api.model.players.model.statistics.Cards;
import com.footystars.foot8.api.model.players.model.statistics.Dribbles;
import com.footystars.foot8.api.model.players.model.statistics.Duels;
import com.footystars.foot8.api.model.players.model.statistics.Fouls;
import com.footystars.foot8.api.model.players.model.statistics.Games;
import com.footystars.foot8.api.model.players.model.statistics.Goals;
import com.footystars.foot8.api.model.players.model.statistics.Passes;
import com.footystars.foot8.api.model.players.model.statistics.Penalty;
import com.footystars.foot8.api.model.players.model.statistics.Shots;
import com.footystars.foot8.api.model.players.model.statistics.Substitutes;
import com.footystars.foot8.api.model.players.model.statistics.Tackles;
import com.footystars.foot8.api.model.teams.statistics.model.league.LeagueResponse;
import com.footystars.foot8.api.model.teams.statistics.model.team.TeamDto;
import com.footystars.foot8.persistence.entities.players.Player;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Embedded
    private TeamDto team;

    @Embedded
    private LeagueResponse league;

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