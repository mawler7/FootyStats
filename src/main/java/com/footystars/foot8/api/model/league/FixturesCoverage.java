package com.footystars.foot8.api.model.league;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class FixturesCoverage {

    private boolean events;
    private boolean lineups;
    @JsonProperty("statistics_fixtures")
    private boolean statisticsFixtures;
    @JsonProperty("statistics_players")
    private boolean statisticsPlayers;
}
