package com.footystars.foot8.api.model.predictions.response.teams.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class PenaltyStats implements Serializable {

    @Embedded
    private ScoredStats scored;

    @Embedded
    private ScoredStats missed;

    private Integer total;

}
