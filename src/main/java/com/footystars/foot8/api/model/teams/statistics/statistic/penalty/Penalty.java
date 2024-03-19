package com.footystars.foot8.api.model.teams.statistics.statistic.penalty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.statistic.penalty.missed.PenaltyMissed;
import com.footystars.foot8.api.model.teams.statistics.statistic.penalty.scored.PenaltyScored;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Penalty implements Serializable {

    private PenaltyMissed missed;
    private PenaltyScored scored;
    private Long total;

}
