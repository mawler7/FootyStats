package com.footystars.foot8.api.model.teams.statistics.model.fixtures.wins;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class FixtureWins implements Serializable {

    @JsonProperty("home")
    private int fixtureWinsHome;

    @JsonProperty("away")
    private int fixtureWinsAway;

    @JsonProperty("total")
    private int totalFixtureWins;


}
