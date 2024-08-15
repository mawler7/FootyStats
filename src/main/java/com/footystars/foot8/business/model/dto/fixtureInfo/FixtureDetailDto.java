package com.footystars.foot8.business.model.dto.fixtureInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.fixture.info.FixtureInfo;
import com.footystars.foot8.api.model.fixtures.fixture.score.Score;
import com.footystars.foot8.business.model.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureDetailDto {

    private Long id;
    private FixtureInfo info;
    private Score score;
    private TeamDto homeTeam;

}
