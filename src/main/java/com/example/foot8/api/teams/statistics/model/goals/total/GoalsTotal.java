package com.example.foot8.api.teams.statistics.model.goals.total;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsTotal implements Serializable {

    @JsonProperty("home")
    private Integer home;

    @JsonProperty("away")
    private Integer away;

}
