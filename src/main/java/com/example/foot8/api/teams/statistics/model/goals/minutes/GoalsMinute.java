package com.example.foot8.api.teams.statistics.model.goals.minutes;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;


@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GoalsMinute implements Serializable {

    @Embedded
    @JsonProperty("0-15")
    private GoalsWithin15MinStatistic from0To15;

    @Embedded
    @JsonProperty("16-30")
    private GoalsWithin15MinStatistic from16To30;

    @Embedded
    @JsonProperty("31-45")
    private GoalsWithin15MinStatistic from31To45;

    @Embedded
    @JsonProperty("46-60")
    private GoalsWithin15MinStatistic from46To60;

    @Embedded
    @JsonProperty("61-75")
    private GoalsWithin15MinStatistic from61To75;

    @Embedded
    @JsonProperty("76-90")
    private GoalsWithin15MinStatistic from76To90;

    @Embedded
    @JsonProperty("91-105")
    private GoalsWithin15MinStatistic from91To105;

    @Embedded
    @JsonProperty("106-120")
    private GoalsWithin15MinStatistic from106To120;

}
