package com.example.foot8.api.teams.statistics.model.fixtures.loses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
