package com.footystars.foot8.api.model.odds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.fixtures.fixture.league.League;
import com.footystars.foot8.api.model.fixtures.Fixtures;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Odds {
    private Fixtures fixture;
    private League league;
    private String update;
    @JsonProperty("bookmakers")
    private List<BookmakerApi> bookmakers;
}
