package com.example.foot8.api.teams.statistics.model.goals;

import com.example.foot8.api.teams.statistics.model.goals.average.GoalsAverageScored;
import com.example.foot8.api.teams.statistics.model.goals.minutes.GoalsPerMinutesScored;
import com.example.foot8.api.teams.statistics.model.goals.total.GoalsTotalScored;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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
