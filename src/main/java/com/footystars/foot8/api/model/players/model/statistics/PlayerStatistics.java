package com.footystars.foot8.api.model.players.model.statistics;

import com.footystars.foot8.api.model.teams.statistics.model.league.LeagueResponse;
import com.footystars.foot8.api.model.teams.statistics.model.team.TeamDto;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlayerStatistics {

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
    @AttributeOverride(name = "committed", column = @Column(name = "fouls_committed"))
    private Fouls fouls;

    @Embedded
    private Cards cards;

    @Embedded
    @AttributeOverride(name = "won", column = @Column(name = "penalties_won"))
    @AttributeOverride(name = "committed", column = @Column(name = "penalties_committed"))
    private Penalty penalty;
}
