package com.example.foot8.api.teams.statistics.model.goals;

import com.example.foot8.api.teams.statistics.model.goals.average.GoalsAverage;
import com.example.foot8.api.teams.statistics.model.goals.minutes.GoalsMinute;
import com.example.foot8.api.teams.statistics.model.goals.total.GoalsTotal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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
