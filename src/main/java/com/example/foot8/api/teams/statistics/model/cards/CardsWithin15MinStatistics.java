package com.example.foot8.api.teams.statistics.model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
