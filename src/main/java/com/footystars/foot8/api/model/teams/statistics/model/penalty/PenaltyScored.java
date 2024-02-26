package com.footystars.foot8.api.model.teams.statistics.model.penalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class PenaltyScored implements Serializable {

    @JsonProperty("percentage")
    private String penaltyScoredPercentage;

    @JsonProperty("total")
    private Long penaltyScoredTotal;
}
