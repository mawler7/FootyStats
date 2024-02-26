package com.footystars.foot8.api.model.teams.statistics.model.league;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class LeagueResponse implements Serializable {

    @JsonProperty("id")
    private Long leagueId;

    @JsonProperty("name")
    private String leagueName;

    @JsonProperty("type")
    private String leagueType;

    @JsonProperty("logo")
    private String leagueLogo;

    @JsonProperty("season")
    private Integer seasonYear;
}
