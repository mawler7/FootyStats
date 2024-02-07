package com.example.foot8.api.teams.statistics.model.fixtures.draws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class HomeAwayTotalStatistic implements Serializable {

    @JsonProperty(namespace = "home")
    private Integer home;

    @JsonProperty(namespace = "away")
    private Integer away;

    @JsonProperty(namespace = "total")
    private Integer total;
}
