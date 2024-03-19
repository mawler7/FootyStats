package com.footystars.foot8.api.model.fixtures.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.fixtures.statistics.statistic.Statistic;
import com.footystars.foot8.api.model.fixtures.statistics.team.Team;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistics implements Serializable {

    @JsonProperty("statistics")
    private List<Statistic> statisticList;
    private Team team;
}
