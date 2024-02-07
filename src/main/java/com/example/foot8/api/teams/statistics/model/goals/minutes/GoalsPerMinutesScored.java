package com.example.foot8.api.teams.statistics.model.goals.minutes;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;


@Getter
@Setter
@Embeddable
public class GoalsPerMinutesScored implements Serializable {

    @Embedded
    @JsonProperty(namespace = "0-15")
    private GoalsWithin15MinStatistic from0To15;

    @Embedded
    @JsonProperty(namespace = "16-30")
    private GoalsWithin15MinStatistic from16To30;

    @Embedded
    @JsonProperty(namespace = "31-45")
    private GoalsWithin15MinStatistic from31To45;

    @Embedded
    @JsonProperty(namespace = "46-60")
    private GoalsWithin15MinStatistic from46To60;

    @Embedded
    @JsonProperty(namespace = "60-75")
    private GoalsWithin15MinStatistic from61To75;

    @Embedded
    @JsonProperty(namespace = "76-90")
    private GoalsWithin15MinStatistic from76To90;

    @Embedded
    @JsonProperty(namespace = "91-105")
    private GoalsWithin15MinStatistic from91To105;

    @Embedded
    @JsonProperty(namespace = "106-120")
    private GoalsWithin15MinStatistic from106To120;

}
