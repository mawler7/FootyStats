package com.footystars.foot8.api.model.teams.statistics.model.cards;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Yellow implements Serializable {

    @Embedded
    @JsonProperty(namespace = "16-30")
    private YellowCardQuarterHourStatistic from16To30;

    @Embedded
    @JsonProperty(namespace = "31-45")
    private YellowCardQuarterHourStatistic from31To45;

    @Embedded
    @JsonProperty(namespace = "46-60")
    private YellowCardQuarterHourStatistic from46To60;

    @Embedded
    @JsonProperty(namespace = "60-75")
    private YellowCardQuarterHourStatistic from61To75;

    @Embedded
    @JsonProperty(namespace = "76-90")
    private YellowCardQuarterHourStatistic from76To90;

    @Embedded
    @JsonProperty(namespace = "91-105")
    private YellowCardQuarterHourStatistic from91To105;

    @Embedded
    @JsonProperty(namespace = "106-120")
    private YellowCardQuarterHourStatistic from106To120;

    @Embedded
    @JsonProperty(namespace = "0-15")
    private YellowCardQuarterHourStatistic from0To15;

}
