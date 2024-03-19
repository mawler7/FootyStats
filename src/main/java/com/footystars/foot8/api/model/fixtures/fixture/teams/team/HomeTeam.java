package com.footystars.foot8.api.model.fixtures.fixture.teams.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class HomeTeam implements Serializable {

    @JsonProperty("id")
    private Long teamId;
    private String name;
    private String logo;
    private Boolean winner;

}
