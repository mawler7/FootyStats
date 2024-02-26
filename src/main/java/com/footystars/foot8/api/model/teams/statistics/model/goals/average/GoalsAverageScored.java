package com.footystars.foot8.api.model.teams.statistics.model.goals.average;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsAverageScored implements Serializable {

    @JsonProperty("away")
    private String goalsAverageAwayScored;

    @JsonProperty("home")
    private String goalsAverageHomeScored;

    @JsonProperty("total")
    private String goalsAverageTotalScored;
}
