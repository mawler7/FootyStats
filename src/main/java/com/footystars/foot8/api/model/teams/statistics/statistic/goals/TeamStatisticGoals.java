package com.footystars.foot8.api.model.teams.statistics.statistic.goals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.goals.total.GoalsTotal;
import com.footystars.foot8.api.model.teams.statistics.statistic.goals.average.GoalsAverage;
import com.footystars.foot8.api.model.teams.statistics.statistic.goals.minutes.GoalsMinutes;
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
public class TeamStatisticGoals implements Serializable {

    private GoalsAverage average;
    private GoalsMinutes minute;
    private GoalsTotal total;

}
