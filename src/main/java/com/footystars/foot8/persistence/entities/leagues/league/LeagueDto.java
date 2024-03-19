package com.footystars.foot8.persistence.entities.leagues.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.leagues.info.LeagueInfo;
import com.footystars.foot8.persistence.entities.countries.CountryDto;
import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeasonDto;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueDto implements Serializable {

    @JsonProperty("league")
    private LeagueInfo leagueInfo;
    private CountryDto country;
    private Set<LeagueSeasonDto> seasons;
}