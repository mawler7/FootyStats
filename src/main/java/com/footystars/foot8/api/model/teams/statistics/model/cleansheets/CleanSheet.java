package com.footystars.foot8.api.model.teams.statistics.model.cleansheets;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class CleanSheet implements Serializable {

    @JsonProperty(namespace = "home")
    private Integer home;

    @JsonProperty(namespace = "away")
    private Integer away;

    @JsonProperty(namespace = "total")
    private Integer total;

}
