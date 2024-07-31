package com.footystars.foot8.api.model.odds.odd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.fixture.info.FixtureInfo;
import com.footystars.foot8.api.model.fixtures.fixture.league.League;
import com.footystars.foot8.api.model.odds.bookmaker.Bookmaker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Odd implements Serializable {
    private FixtureInfo fixture;
    private League league;
    private ZonedDateTime update;
    private List<Bookmaker> bookmakers;
}
