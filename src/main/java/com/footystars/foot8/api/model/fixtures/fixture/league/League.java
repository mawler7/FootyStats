package com.footystars.foot8.api.model.fixtures.fixture.league;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class League implements Serializable {

    @JsonProperty("id")
    private Long leagueId;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private Integer season;
    private String round;

}