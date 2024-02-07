package com.example.foot8.api.teams.statistics.model.goals.average;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
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
