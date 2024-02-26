package com.footystars.foot8.api.model.teams.statistics.model.fixtures.loses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class FixtureLoses implements Serializable {

    @JsonProperty("home")
    private int fixtureLosesHome;

    @JsonProperty("away")
    private int fixtureLosesAway;

    @JsonProperty("total")
    private int fixtureLosesTotal;

}
