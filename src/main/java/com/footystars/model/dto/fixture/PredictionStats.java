package com.footystars.model.dto.fixture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
    public  class PredictionStats {
    private long adviceCorrectCount;
    private long totalAdvice;
    private double adviceCorrectPercentage;

    private long homeGoalsCorrectCount;
    private long totalHomeGoals;
    private double homeGoalsCorrectPercentage;

    private long awayGoalsCorrectCount;
    private long totalAwayGoals;
    private double awayGoalsCorrectPercentage;

    private long overUnderCorrectCount;
    private long totalOverUnder;
    private double overUnderCorrectPercentage;

    private long totalPredictions;

}
