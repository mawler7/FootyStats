package com.footystars.foot8.api.model.fixtures.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.events.event.FixtureEvent;
import com.footystars.foot8.api.model.fixtures.fixture.goals.Goals;
import com.footystars.foot8.api.model.fixtures.fixture.info.FixtureInfo;
import com.footystars.foot8.api.model.fixtures.fixture.league.League;
import com.footystars.foot8.api.model.fixtures.fixture.players.FixturePlayers;
import com.footystars.foot8.api.model.fixtures.fixture.score.Score;
import com.footystars.foot8.api.model.fixtures.fixture.teams.Teams;
import com.footystars.foot8.api.model.fixtures.statistics.statistic.Statistic;
import com.footystars.foot8.api.model.lineups.lineup.players.LineupPlayers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureApi implements Serializable {
    private FixtureInfo fixture;
    private League league;
    private Teams teams;
    private Goals goals;
    private Score score;
    private List<FixtureEvent> events;
    private List<Statistic> statistics;
    private List<LineupPlayers> lineups;
    private List<FixturePlayers> players;
}