package com.footystars.foot8.api.model.teams.statistics.model.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.model.goals.average.GoalsAverage;
import com.footystars.foot8.api.model.teams.statistics.model.goals.minutes.GoalsMinute;
import com.footystars.foot8.api.model.teams.statistics.model.goals.total.GoalsTotal;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TeamStatisticGoals implements Serializable {

    @Embedded
    @JsonProperty("average")
    private GoalsAverage average;

    @Embedded
    @JsonProperty("minute")
    private GoalsMinute minute;

    @Embedded
    @JsonProperty("total")
    private GoalsTotal total;


}
