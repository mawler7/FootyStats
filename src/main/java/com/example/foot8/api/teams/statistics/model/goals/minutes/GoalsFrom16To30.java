package com.example.foot8.api.teams.statistics.model.goals.minutes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsFrom16To30 implements Serializable {

    @JsonProperty("percentage")
    private String goalsFrom16To30Percentage;

    @JsonProperty("total")
    private Integer goalsFrom16To30Total;
}
