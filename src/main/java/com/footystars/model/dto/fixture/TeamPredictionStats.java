package com.footystars.model.dto.fixture;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeamPredictionStats {
    private Long teamId;
    private String teamName;
    private Double adviceAccuracy;
    private Double homePredictionAccuracy;
    private Double awayPredictionAccuracy;
    private Double overUnderAccuracy;

    public TeamPredictionStats(Long teamId, String teamName, Double adviceAccuracy, Double homePredictionAccuracy, Double awayPredictionAccuracy, Double overUnderAccuracy) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.adviceAccuracy = adviceAccuracy;
        this.homePredictionAccuracy = homePredictionAccuracy;
        this.awayPredictionAccuracy = awayPredictionAccuracy;
        this.overUnderAccuracy = overUnderAccuracy;
    }
}
