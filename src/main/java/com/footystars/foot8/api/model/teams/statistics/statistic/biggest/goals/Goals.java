package com.footystars.foot8.api.model.teams.statistics.statistic.biggest.goals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.goals.total.GoalsTotal;
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
public class Goals implements Serializable {

    @JsonProperty("for")
    private GoalsTotal goalsFor;
    private GoalsTotal against;

}