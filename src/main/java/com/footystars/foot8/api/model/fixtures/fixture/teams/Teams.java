package com.footystars.foot8.api.model.fixtures.fixture.teams;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.fixtures.fixture.teams.team.AwayTeam;
import com.footystars.foot8.api.model.fixtures.fixture.teams.team.HomeTeam;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Teams implements Serializable {

    @JsonProperty("home")
    private HomeTeam homeTeam;

    @JsonProperty("away")
    private AwayTeam awayTeam;
}

