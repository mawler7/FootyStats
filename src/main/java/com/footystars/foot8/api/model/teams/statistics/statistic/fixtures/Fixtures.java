package com.footystars.foot8.api.model.teams.statistics.statistic.fixtures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.wins.Wins;
import com.footystars.foot8.api.model.teams.statistics.statistic.fixtures.draws.Draws;
import com.footystars.foot8.api.model.teams.statistics.statistic.fixtures.loses.Loses;
import com.footystars.foot8.api.model.teams.statistics.statistic.fixtures.played.Played;
import jakarta.persistence.Embeddable;
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
public class Fixtures implements Serializable {

    private Draws draws;
    private Loses loses;
    private Played played;
    private Wins wins;
}
