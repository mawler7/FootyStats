package com.footystars.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.api.Fixtures;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureStatisticDto implements Serializable {
  private Fixtures.FixtureDto.Statistics.TeamFixture team;
  private String type;
  private String value;
}