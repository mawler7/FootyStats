package com.footystars.foot8.api.model.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
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
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueInfo implements Serializable {

    @JsonProperty("id")
    private Long leagueId;

    @JsonProperty("name")
    private String leagueName;

    @JsonProperty("type")
    private String leagueType;

    @JsonProperty("logo")
    private String leagueLogo;
}