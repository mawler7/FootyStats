package com.footystars.model.dto.fixture;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaguePredictionsDto {
    private Long leagueId;
    private String leagueName;
    private Double adviceAccuracy;
    private Double homePredictionAccuracy;
    private Double awayPredictionAccuracy;
    private Double overUnderAccuracy;

    public LeaguePredictionsDto(Long leagueId, String leagueName, Double adviceAccuracy, Double homePredictionAccuracy, Double awayPredictionAccuracy, Double overUnderAccuracy) {
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.adviceAccuracy = adviceAccuracy;
        this.homePredictionAccuracy = homePredictionAccuracy;
        this.awayPredictionAccuracy = awayPredictionAccuracy;
        this.overUnderAccuracy = overUnderAccuracy;
    }
}