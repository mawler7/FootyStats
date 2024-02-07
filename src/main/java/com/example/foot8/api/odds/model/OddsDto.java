package com.example.foot8.api.odds.model;

import com.example.foot8.api.fixture.model.FixtureDto;
import com.example.foot8.api.fixture.model.LeagueDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private FixtureDto fixture;
    private LeagueDto league;
    private String update;
    @JsonProperty("bookmakers")
    private List<BookmakerDto> bookmakersDto;
}
