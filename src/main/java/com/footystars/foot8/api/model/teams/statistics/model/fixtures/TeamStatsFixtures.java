package com.footystars.foot8.api.model.teams.statistics.model.fixtures;

import com.footystars.foot8.api.model.teams.statistics.model.fixtures.draws.HomeAwayTotalStatistic;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class TeamStatsFixtures implements Serializable {

    @Embedded
    private HomeAwayTotalStatistic draws;

    @Embedded
    private HomeAwayTotalStatistic loses;

    @Embedded
    private HomeAwayTotalStatistic played;

    @Embedded
    private HomeAwayTotalStatistic wins;
}
