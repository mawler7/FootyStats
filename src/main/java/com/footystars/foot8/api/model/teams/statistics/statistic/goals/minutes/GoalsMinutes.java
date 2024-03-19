package com.footystars.foot8.api.model.teams.statistics.statistic.goals.minutes;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.statistic.goals.minutes.stat.GoalsStat;
import jakarta.persistence.Embeddable;
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
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoalsMinutes implements Serializable {

    @JsonProperty("0-15")
    private GoalsStat from0To15;
    @JsonProperty("16-30")
    private GoalsStat from16To30;
    @JsonProperty("31-45")
    private GoalsStat from31To45;
    @JsonProperty("46-60")
    private GoalsStat from46To60;
    @JsonProperty("61-75")
    private GoalsStat from61To75;
    @JsonProperty("76-90")
    private GoalsStat from76To90;
    @JsonProperty("91-105")
    private GoalsStat from91To105;
    @JsonProperty("106-120")
    private GoalsStat from106To120;

}
