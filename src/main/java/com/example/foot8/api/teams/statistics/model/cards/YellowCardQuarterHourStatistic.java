package com.example.foot8.api.teams.statistics.model.cards;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class YellowCardQuarterHourStatistic implements Serializable {

    private String percentage;
    private Integer total;
}
