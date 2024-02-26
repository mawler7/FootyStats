package com.footystars.foot8.api.model.teams.statistics.model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class CardsWithin15MinStatistics implements Serializable {

    @JsonProperty("percentage")
    private String percentage;

    @JsonProperty("total")
    private Integer total;
}
