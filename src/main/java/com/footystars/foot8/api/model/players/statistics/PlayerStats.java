package com.footystars.foot8.api.model.players.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.players.statistics.cards.Cards;
import com.footystars.foot8.api.model.players.statistics.dribbles.Dribbles;
import com.footystars.foot8.api.model.players.statistics.duels.Duels;
import com.footystars.foot8.api.model.players.statistics.fouls.Fouls;
import com.footystars.foot8.api.model.players.statistics.games.Games;
import com.footystars.foot8.api.model.players.statistics.goals.Goals;
import com.footystars.foot8.api.model.players.statistics.passes.Passes;
import com.footystars.foot8.api.model.players.statistics.penalty.Penalty;
import com.footystars.foot8.api.model.players.statistics.shots.Shots;
import com.footystars.foot8.api.model.players.statistics.substitutes.Substitutes;
import com.footystars.foot8.api.model.players.statistics.tackles.Tackles;
import com.footystars.foot8.api.model.teams.statistics.statistic.league.League;
import com.footystars.foot8.api.model.teams.statistics.statistic.team.Club;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStats implements Serializable {
    @JsonProperty("team")
    @Embedded
    private Club club;
    @Embedded
    private League league;
    @Embedded
    private Games games;
    @Embedded
    private Substitutes substitutes;
    @Embedded
    private Shots shots;
    @Embedded
    private Goals goals;
    @Embedded
    private Passes passes;
    @Embedded
    private Tackles tackles;
    @Embedded
    private Duels duels;
    @Embedded
    private Dribbles dribbles;
    @Embedded
    private Fouls fouls;
    @Embedded
    private Cards cards;
    @Embedded
    private Penalty penalty;
}
