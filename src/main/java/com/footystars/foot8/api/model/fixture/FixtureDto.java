package com.footystars.foot8.api.model.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
@Embeddable
public class FixtureDto implements Serializable {
    @Embedded
    private FixtureInfo fixture;
    @Embedded
    private FixtureLeagueDto league;
    @Embedded
    private TeamsDto teams;
    @Embedded
    private GoalsDto goals;
    @Embedded
    private ScoreDto score;
}
