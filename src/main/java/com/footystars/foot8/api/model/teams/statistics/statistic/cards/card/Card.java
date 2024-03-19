package com.footystars.foot8.api.model.teams.statistics.statistic.cards.card;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.statistic.cards.card.stat.CardStats;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card implements Serializable {

    @JsonProperty("0-15")
    private CardStats from0To15;

    @JsonProperty("16-30")
    private CardStats from16To30;

    @JsonProperty("31-45")
    private CardStats from31To45;

    @JsonProperty("46-60")
    private CardStats from46To60;

    @JsonProperty("61-75")
    private CardStats from61To75;

    @JsonProperty("76-90")
    private CardStats from76To90;

    @JsonProperty("91-105")
    private CardStats from91To105;

    @JsonProperty("106-120")
    private CardStats from106To120;
}

