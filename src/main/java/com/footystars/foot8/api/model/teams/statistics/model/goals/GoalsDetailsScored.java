package com.footystars.foot8.api.model.teams.statistics.model.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.model.goals.average.GoalsAverageScored;
import com.footystars.foot8.api.model.teams.statistics.model.goals.minutes.GoalsPerMinutesScored;
import com.footystars.foot8.api.model.teams.statistics.model.goals.total.GoalsTotalScored;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class GoalsDetailsScored implements Serializable {

    @Embedded
    private GoalsAverageScored average;

    @Embedded
    private GoalsPerMinutesScored minutes;

    @Embedded
    @JsonProperty("total")
    private GoalsTotalScored goalsTotal;


}
