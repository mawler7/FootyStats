package com.footystars.foot8.api.model.teams.statistics.model.cards;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class YellowCardQuarterHourStatistic implements Serializable {

    private String percentage;
    private Integer total;
}
