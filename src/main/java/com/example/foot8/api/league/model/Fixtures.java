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
public class Fixtures {
    private boolean events;
    private boolean lineups;
    @JsonProperty("statistics_fixtures")
    private boolean statisticsFixtures;
    @JsonProperty("statistics_players")
    private boolean statisticsPlayers;
}
