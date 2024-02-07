package com.example.foot8.api.teams.statistics.model.penalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class PenaltyScored {

    @JsonProperty("percentage")
    private String penaltyScoredPercentage;

    @JsonProperty("total")
    private Long penaltyScoredTotal;
}
