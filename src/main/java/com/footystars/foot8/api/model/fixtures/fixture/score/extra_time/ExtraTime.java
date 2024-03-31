package com.footystars.foot8.api.model.fixtures.fixture.score.extra_time;

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
public class ExtraTime implements Serializable {
    private Long home;
    private Long away;
}
