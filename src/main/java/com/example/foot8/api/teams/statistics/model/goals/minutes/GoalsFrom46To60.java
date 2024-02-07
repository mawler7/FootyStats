package com.example.foot8.api.teams.statistics.model.goals.minutes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsFrom46To60 implements Serializable {

    @JsonProperty("percentage")
    private String goalsFrom46To60Percentage;

    @JsonProperty("total")
    private Integer goalsFrom46To60Total;
}
