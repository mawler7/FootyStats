package com.example.foot8.api.teams.statistics.model.penalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Setter
@Embeddable
public class Penalty {

    @Embedded
    private PenaltyMissed missed;

    @Embedded
    private PenaltyScored scored;


    @JsonProperty("total")
    private Long totalPenalties;

}
