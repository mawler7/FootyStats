package com.example.foot8.api.league.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Coverage {

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