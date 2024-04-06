package com.footystars.foot8.api.model.predictions.response.teams.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class FixtureStats implements Serializable {


    private PlayedStats played;

    private PlayedStats wins;

    private PlayedStats draws;

    private PlayedStats loses;

}
