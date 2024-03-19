package com.footystars.foot8.persistence.entities.leagues.seaon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.fixtures.fixture.LeagueFixture;
import com.footystars.foot8.persistence.entities.fixtures.fixture.FixtureDto;
import com.footystars.foot8.persistence.entities.leagues.league.LeagueDto;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeasonDto;
import com.footystars.foot8.persistence.entities.teams.standings.TeamStandingDto;
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
public class LeagueSeasonDto implements Serializable {
    private Long id;
    private int year;
    @JsonProperty("start")
    private String startDate;
    @JsonProperty("end")
    private String endDate;
    private Boolean current;
    private LeagueDto league;

    private Set<TeamStandingDto> standings;
    private Set<FixtureDto> fixtures;
    private Set<TeamSeasonDto> teamSeasons;
}