package com.footystars.foot8.api.model.teams.statistics.statistic.cards.card.stat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
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
public class CardStats implements Serializable {

    private String percentage;
    private Integer total;
}
