package com.footystars.foot8.api.model.teams.statistics.model.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class TeamDto implements Serializable {

    @JsonProperty("id")
    private Long teamId;

    @JsonProperty("logo")
    private String teamLogo;

    @JsonProperty("name")
    private String teamName;

}
