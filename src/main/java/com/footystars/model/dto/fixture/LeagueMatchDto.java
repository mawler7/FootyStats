package com.footystars.model.dto.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * DTO representing a match with league and team details.
 */
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueMatchDto {

    private Long id;
    private String date;
    private Long homeTeamId;
    private String homeTeamName;
    private String homeTeamLogo;
    private Long awayTeamId;
    private String awayTeamName;
    private String awayTeamLogo;
    private String leagueName;
    private String leagueLogo;
    private Long leagueId;
    private Integer season;
    private String round;
    private String elapsed;
    private String status;
    private MatchScoreDto matchScore;

    /**
     * Constructs a LeagueMatchDto object with match details.
     *
     * @param id           The match ID.
     * @param date         The date of the match.
     * @param homeTeamId   The ID of the home team.
     * @param homeTeamName The name of the home team.
     * @param homeTeamLogo The logo of the home team.
     * @param awayTeamId   The ID of the away team.
     * @param awayTeamName The name of the away team.
     * @param awayTeamLogo The logo of the away team.
     * @param leagueName   The name of the league.
     * @param leagueLogo   The logo of the league.
     * @param leagueId     The ID of the league.
     * @param season       The season year.
     * @param round        The round of the match.
     * @param elapsed      The elapsed time of the match.
     * @param status       The match status.
     * @param matchScore   The match score details.
     */
    public LeagueMatchDto(Long id, String date, Long homeTeamId, String homeTeamName, String homeTeamLogo,
                          Long awayTeamId, String awayTeamName, String awayTeamLogo, String leagueName, String leagueLogo,
                          Long leagueId, Integer season, String round, String elapsed, String status,
                          MatchScoreDto matchScore) {
        this.id = id;
        this.date = date;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.homeTeamLogo = homeTeamLogo;
        this.awayTeamId = awayTeamId;
        this.awayTeamName = awayTeamName;
        this.awayTeamLogo = awayTeamLogo;
        this.leagueName = leagueName;
        this.leagueLogo = leagueLogo;
        this.leagueId = leagueId;
        this.season = season;
        this.round = round;
        this.elapsed = elapsed;
        this.status = status;
        this.matchScore = matchScore;
    }
}
