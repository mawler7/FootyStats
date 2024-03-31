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
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStatisticApi implements Serializable {
    @JsonProperty("team")
    private Club club;
    private League league;
    private Games games;
    private Substitutes substitutes;
    private Shots shots;
    private Goals goals;
    private Passes passes;
    private Tackles tackles;
    private Duels duels;
    private Dribbles dribbles;
    private Fouls fouls;
    private Cards cards;
    private Penalty penalty;
}
