package com.footystars.foot8.api.model.leagues.league.coverage;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.footystars.foot8.api.model.leagues.league.coverage.fixtures.Fixtures;
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
public class Coverage implements Serializable {

    @Embedded
    private Fixtures fixtures;
    private boolean standings;
    private boolean players;
    @JsonProperty("top_scorers")
    private boolean topScorers;
    @JsonProperty("top_assists")
    private boolean topAssists;
    @JsonProperty("top_cards")
    private boolean topCards;
    private boolean injuries;
    private boolean predictions;
    private boolean odds;
}