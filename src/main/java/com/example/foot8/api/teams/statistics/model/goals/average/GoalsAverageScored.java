package com.example.foot8.api.teams.statistics.model.goals.average;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
