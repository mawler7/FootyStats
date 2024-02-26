package com.footystars.foot8.api.model.teams.statistics.model.biggest.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsScored implements Serializable {

    @JsonProperty("home")
    private int goalsScoredHome;

    @JsonProperty("away")
    private int goalsScoredAway;

    @JsonProperty("total")
    private int goalsScoredTotal;


}
