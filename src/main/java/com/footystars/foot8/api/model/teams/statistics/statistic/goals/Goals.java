package com.footystars.foot8.api.model.teams.statistics.statistic.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Goals implements Serializable {

    @JsonProperty("for")
    private TeamStatisticGoals goalsFor;
    private TeamStatisticGoals against;

}