package com.footystars.foot8.api.model.predictions.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.predictions.response.h2h.FixtureH2H;
import com.footystars.foot8.api.model.predictions.response.predictions.info.Goals;
import com.footystars.foot8.api.model.predictions.response.teams.league.ScoredStats;
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
public class H2HData implements Serializable {

    private FixtureH2H fixture;
    private TeamsData teams;
    private Goals goals;
    private ScoredStats score;

}
