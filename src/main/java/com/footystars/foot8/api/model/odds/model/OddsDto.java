package com.footystars.foot8.api.model.odds.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.fixture.FixtureLeagueDto;
import com.footystars.foot8.api.model.fixture.FixturesResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OddsDto {
    private FixturesResponse fixture;
    private FixtureLeagueDto league;
    private String update;
    @JsonProperty("bookmakers")
    private List<BookmakerDto> bookmakersDto;
}
