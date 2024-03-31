package com.footystars.foot8.api.model.fixtures.fixture.info.periods;

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
public class Periods implements Serializable {

    private Long first;
    private Long second;

}
