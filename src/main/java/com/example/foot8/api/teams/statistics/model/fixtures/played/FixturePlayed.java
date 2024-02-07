package com.example.foot8.api.teams.statistics.model.fixtures.played;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
