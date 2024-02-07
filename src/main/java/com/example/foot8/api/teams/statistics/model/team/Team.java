package com.example.foot8.api.teams.statistics.model.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Team {

    @JsonProperty("id")
    private Long teamId;

    @JsonProperty("logo")
    private String teamLogo;

    @JsonProperty("name")
    private String teamName;

}
