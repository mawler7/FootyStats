package com.footystars.foot8.api.model.teams.statistics.model.penalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Embeddable
public class PenaltyMissed implements Serializable {

    @JsonProperty("percentage")
    private String penaltyMissedPercentage;

    @JsonProperty("total")
    private Long penaltyMissedTotal;
}
