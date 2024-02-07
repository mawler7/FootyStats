package com.example.foot8.api.teams.statistics.model.penalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class PenaltyMissed {

    @JsonProperty("percentage")
    private String penaltyMissedPercentage;

    @JsonProperty("total")
    private Long penaltyMissedTotal;
}
