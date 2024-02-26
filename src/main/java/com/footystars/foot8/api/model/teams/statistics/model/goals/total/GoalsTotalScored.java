package com.footystars.foot8.api.model.teams.statistics.model.goals.total;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsTotalScored implements Serializable {

    @JsonProperty("home")
    private int goalsTotalHomeScored;

    @JsonProperty("away")
    private int goalsTotalAwayScored;

    @JsonProperty("total")
    private int totalScored;

}
