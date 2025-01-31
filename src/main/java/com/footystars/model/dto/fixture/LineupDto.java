package com.footystars.model.dto.fixture;

import com.footystars.model.api.Fixtures;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
public class LineupDto implements Serializable {
   private Fixtures.FixtureDto.FixtureTeam team;
   private String formation;
   private Fixtures.FixtureDto.CoachDto coach;
   private Set<Fixtures.FixtureDto.Lineup.PlayerStartXI> startXI;
   private Set<Fixtures.FixtureDto.Lineup.PlayerSubstitutes> substitutes;
}