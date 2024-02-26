package com.footystars.foot8.api.model.fixture;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
@Embeddable
public class ScoreDto implements Serializable {
    @Embedded
    private HalfTimeDto halftime;
    @Embedded
    private FullTimeDto fulltime;
    @Embedded
    private ExtraTimeDto extratime;
    @Embedded
    private PenaltyDto penalty;
}