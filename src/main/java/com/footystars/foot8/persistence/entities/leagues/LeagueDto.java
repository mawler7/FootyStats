package com.footystars.foot8.persistence.entities.leagues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixture.FixtureDto;
import com.footystars.foot8.api.model.teams.statistics.model.team.TeamDto;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueDto implements Serializable {
    private Long id;

    private Long leagueId;
    private String leagueName;
    private String leagueLogo;
    private String leagueType;

    private String countryName;
    private String countryCode;
    private String countryFlag;

    private Integer seasonYear;
    private String startDate;
    private String endDate;
    private boolean current;

    @Embedded
    private Set<TeamDto> teams;
    @Embedded
    private Set<FixtureDto> fixtures;
}