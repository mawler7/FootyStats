package com.footystars.foot8.api.model.teams.statistics.model.biggest.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsConceded implements Serializable {

    @JsonProperty("home")
    private int home;

    @JsonProperty("away")
    private int away;

    @JsonProperty("total")
    private int total;


}
