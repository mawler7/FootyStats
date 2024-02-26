package com.footystars.foot8.api.model.teams.statistics.model.goals.average;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GoalsAverage implements Serializable {

    @JsonProperty("away")
    private String away;

    @JsonProperty("home")
    private String home;

    @JsonProperty("total")
    private String total;
}
