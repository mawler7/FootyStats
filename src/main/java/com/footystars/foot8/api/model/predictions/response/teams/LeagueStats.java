package com.footystars.foot8.api.model.predictions.response.teams;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.predictions.response.teams.league.BiggestStats;
import com.footystars.foot8.api.model.predictions.response.teams.league.CardStats;
import com.footystars.foot8.api.model.predictions.response.teams.league.FixtureStats;
import com.footystars.foot8.api.model.predictions.response.teams.league.GoalsStats;
import com.footystars.foot8.api.model.predictions.response.teams.league.PenaltyStats;
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
public class LeagueStats implements Serializable {

    private String form;

    @Embedded
    private FixtureStats fixtures;

    @Embedded
    private GoalsStats goals;

    @Embedded
    private BiggestStats biggest;

    @JsonProperty("clean_sheet")
    private Integer cleanSheet;

    @JsonProperty("failed_to_score")
    private Integer failedToScore;

    @Embedded
    private PenaltyStats penalty;

    @Embedded
    private CardStats cards;

}
