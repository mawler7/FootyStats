package com.footystars.persistence.repository;

import java.time.ZonedDateTime;

public interface PlayerLastMatchDto {
    ZonedDateTime getMatchDate();
    Long getLeagueId();
    String getLeagueName();
    String getHomeTeamName();
    String getAwayTeamName();
    String getHomeTeamLogo();
    String getAwayTeamLogo();
    String getRating();
    Integer getMinutes();
    Integer getGoals();
    Integer getAssists();
    Integer getYellowCards();
    Integer getRedCards();
}
