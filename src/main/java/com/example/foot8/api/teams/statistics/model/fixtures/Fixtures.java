package com.example.foot8.api.teams.statistics.model.fixtures;

import com.example.foot8.api.teams.statistics.model.fixtures.draws.HomeAwayTotalStatistic;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Fixtures implements Serializable {
    
    @Embedded
    private HomeAwayTotalStatistic draws;
   
    @Embedded
    private HomeAwayTotalStatistic loses;
   
    @Embedded
    private HomeAwayTotalStatistic played;
    
    @Embedded
    private HomeAwayTotalStatistic wins;
}
