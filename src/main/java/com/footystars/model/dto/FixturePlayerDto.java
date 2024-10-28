package com.footystars.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.api.Fixtures;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixturePlayerDto implements Serializable {
    private Fixtures.FixtureDto.FixturePlayer.FixturePlayerInfo player;
    private Set<Fixtures.FixtureDto.FixturePlayer.FixturePlayerStats> stats;
}