package com.footystars.foot8.persistence.entity.fixtures.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.persistence.entity.fixtures.fixture.FixtureDto;
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
public class FixtureStatDto implements Serializable {
    private Long id;
    private String type;
    private String value;
    private FixtureDto fixture;
}