package com.example.foot8.api.teams.statistics.model.biggest.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
