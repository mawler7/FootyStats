package com.footystars.foot8.api.model.teams.statistics.model.cards;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card implements Serializable {

    @Embedded
    @JsonProperty("0-15")
    private CardsWithin15MinStatistics from0To15;

    @Embedded
    @JsonProperty("16-30")
    private CardsWithin15MinStatistics from16To30;

    @Embedded
    @JsonProperty("31-45")
    private CardsWithin15MinStatistics from31To45;

    @Embedded
    @JsonProperty("46-60")
    private CardsWithin15MinStatistics from46To60;

    @Embedded
    @JsonProperty("61-75")
    private CardsWithin15MinStatistics from61To75;

    @Embedded
    @JsonProperty("76-90")
    private CardsWithin15MinStatistics from76To90;

    @Embedded
    @JsonProperty("91-105")
    private CardsWithin15MinStatistics from91To105;

    @Embedded
    @JsonProperty("106-120")
    private CardsWithin15MinStatistics from106To120;
}

