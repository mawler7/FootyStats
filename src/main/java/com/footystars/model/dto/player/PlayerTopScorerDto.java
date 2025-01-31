package com.footystars.model.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerTopScorerDto {
    private Long playerId;
    private String name;
    private String nationality;
    private String photo;
    private String clubName;
    private String clubLogo;
    private String position;
    private String form;
    private int appearances;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private int shotsOnTarget;
    private int totalShots;
    private int penaltiesScored;
    private int penaltiesMissed;
    private int minutesPlayed;
    private int keyPasses;
    private int duelsTotal;
    private int duelsWon;

    public Double getGoalsPerGame() {
        return appearances == 0 ? 0.0 : (double) goals / appearances;
    }

    public Double getShotAccuracy() {
        return totalShots == 0 ? 0.0 : (double) shotsOnTarget / totalShots * 100;
    }

    public Double getDuelsWonPercentage() {
        return duelsTotal == 0 ? 0.0 : (double) duelsWon / duelsTotal * 100;
    }
}
