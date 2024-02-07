package com.example.foot8.api.teams.statistics.model.fixtures.wins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
