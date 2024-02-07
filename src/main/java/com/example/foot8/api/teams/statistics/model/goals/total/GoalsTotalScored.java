package com.example.foot8.api.teams.statistics.model.goals.total;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
