package com.footystars.model.dto;

import com.footystars.model.api.Fixtures;
import com.footystars.model.api.Predictions;
import com.footystars.model.entity.Bet;
import com.footystars.model.entity.FixtureEvent;
import com.footystars.model.entity.FixturePlayer;
import com.footystars.model.entity.FixtureStatistic;
import com.footystars.model.entity.Lineup;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class FixtureDto implements Serializable {
    private long id;
    private ZonedDateTime lastUpdated;
    private Fixtures.FixtureDto.FixtureInfo info;
    private Fixtures.FixtureDto.LeagueDto league;
    private Fixtures.FixtureDto.Goals goals;
    private Fixtures.FixtureDto.Score score;
    private Fixtures.FixtureDto.Teams teams;
    private Predictions.PredictionDto prediction;
    private List<Bet> bets = new ArrayList<>();
    private List<Lineup> lineups = new ArrayList<>();
    private List<FixturePlayer> players = new ArrayList<>();
    private List<FixtureEvent> events = new ArrayList<>();
    private List<FixtureStatistic> statistics = new ArrayList<>();
}
