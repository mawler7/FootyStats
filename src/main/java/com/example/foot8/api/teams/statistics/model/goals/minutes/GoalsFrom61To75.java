package com.example.foot8.api.teams.statistics.model.goals.minutes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsFrom61To75 implements Serializable {

    @JsonProperty("percentage")
    private String goalsFrom61To75Percentage;

    @JsonProperty("total")
    private Integer goalsFrom61To75Total;
}
