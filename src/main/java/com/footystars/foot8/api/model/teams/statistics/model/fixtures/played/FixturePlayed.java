package com.footystars.foot8.api.model.teams.statistics.model.fixtures.played;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class FixturePlayed implements Serializable {

    @JsonProperty("home")
    private int fixturePlayedHome;

    @JsonProperty("away")
    private int fixturePlayedAway;

    @JsonProperty("total")
    private int fixturePlayedTotal;

}
