package com.footystars.foot8.api.model.teams.statistics.model.penalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Penalty implements Serializable {

    @Embedded
    private PenaltyMissed missed;

    @Embedded
    private PenaltyScored scored;


    @JsonProperty("total")
    private Long totalPenalties;

}
