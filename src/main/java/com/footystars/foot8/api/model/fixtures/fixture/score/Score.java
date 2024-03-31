package com.footystars.foot8.api.model.fixtures.fixture.score;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.fixture.score.extra_time.ExtraTime;
import com.footystars.foot8.api.model.fixtures.fixture.score.full_time.FullTime;
import com.footystars.foot8.api.model.fixtures.fixture.score.half_time.HalfTime;
import com.footystars.foot8.api.model.fixtures.fixture.score.penalty.Penalty;
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
public class Score implements Serializable {

    private HalfTime halftime;
    private FullTime fulltime;
    private ExtraTime extratime;
    private Penalty penalty;

}