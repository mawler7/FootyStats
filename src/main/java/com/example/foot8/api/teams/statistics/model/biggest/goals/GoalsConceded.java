package com.example.foot8.api.teams.statistics.model.biggest.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
