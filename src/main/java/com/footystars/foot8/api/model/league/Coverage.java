package com.footystars.foot8.api.model.league;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Coverage implements Serializable {

    @Embedded
    private FixturesCoverage fixtures;
    @JsonProperty("top_scorers")
    private boolean topScorers;
    @JsonProperty("top_assists")
    private boolean topAssists;
    @JsonProperty("top_cards")
    private boolean topCards;
    private boolean standings;
    private boolean players;
    private boolean injuries;
    private boolean predictions;
    private boolean odds;
}