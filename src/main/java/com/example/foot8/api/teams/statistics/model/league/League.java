package com.example.foot8.api.teams.statistics.model.league;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class League {

    @JsonProperty("id")
    private Long leagueId;

    @JsonProperty("name")
    private String leagueName;

    @JsonProperty("type")
    private String leagueType;

    @JsonProperty("logo")
    private String leagueLogo;

    @JsonProperty("season")
    private Long leagueSeason;
}
