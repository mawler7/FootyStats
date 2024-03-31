package com.footystars.foot8.api.model.teams.statistics.statistic.biggest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.goals.Goals;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.loses.Loses;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.streak.Streak;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.wins.Wins;
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
public class Biggest implements Serializable {

    @JsonProperty("goals")
    private Goals biggestGoals;
    private Loses loses;
    private Streak streak;
    private Wins wins;

}

