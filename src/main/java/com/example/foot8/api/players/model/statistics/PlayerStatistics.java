package com.example.foot8.api.players.model.statistics;

import com.example.foot8.api.teams.statistics.model.league.League;
import com.example.foot8.api.teams.statistics.model.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlayerStatistics {

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
    @AttributeOverride(name = "committed", column = @Column(name = "fouls_committed"))
    private Fouls fouls;

    @Embedded
    private Cards cards;

    @Embedded
    @AttributeOverride(name = "won", column = @Column(name = "penalties_won"))
    @AttributeOverride(name = "committed", column = @Column(name = "penalties_committed"))
    private Penalty penalty;
}
