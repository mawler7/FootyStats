package com.footystars.foot8.api.model.predictions.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.fixture.teams.team.AwayTeam;
import com.footystars.foot8.api.model.fixtures.fixture.teams.team.HomeTeam;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class TeamsData implements Serializable {

    @Embedded
    private HomeTeam home;
    @Embedded
    private AwayTeam away;

}
