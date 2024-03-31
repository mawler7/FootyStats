package com.footystars.foot8.api.model.fixtures.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.fixture.goals.Goals;
import com.footystars.foot8.api.model.fixtures.fixture.info.FixtureInfo;
import com.footystars.foot8.api.model.fixtures.fixture.league.League;
import com.footystars.foot8.api.model.fixtures.fixture.score.Score;
import com.footystars.foot8.api.model.fixtures.fixture.teams.Teams;
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
public class LeagueFixture implements Serializable {

    private FixtureInfo fixture;
    private League league;
    private Teams teams;
    private Goals goals;
    private Score score;

}