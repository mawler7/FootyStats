package com.footystars.model.dto.fixture;

import com.footystars.model.api.Fixtures;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class FixtureEventDto implements Serializable {
  private  String type;
  private  String detail;
  private  String comments;
  private  String description;
  private  Fixtures.FixtureDto.FixtureEvent.Time time;
  private  Fixtures.FixtureDto.FixtureEvent.Team team;
  private  Fixtures.FixtureDto.FixtureEvent.Player player;
  private  Fixtures.FixtureDto.FixtureEvent.Assist assist;
}