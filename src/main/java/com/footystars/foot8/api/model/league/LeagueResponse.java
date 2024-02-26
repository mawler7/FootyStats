package com.footystars.foot8.api.model.league;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.persistence.entities.countries.CountryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueResponse {

    @JsonProperty("league")
    private LeagueInfo leagueInfo;

    @JsonProperty("country")
    private CountryDto country;

    private List<SeasonDto> seasons;

}
