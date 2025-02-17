package com.footystars.model.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PlayerTeamSquadDto {
    // Dane zawodnika (część info)
    private Long playerId;            // id (oraz player_id)
    private String birthCountry;      // birth_country
    private String birthDate;         // birth_date
    private String birthPlace;        // birth_place
    private String firstname;         // firstname
    private Boolean injured;          // injured
    private String lastname;          // lastname
    private String name;              // name
    private String nationality;       // nationality
    private String photo;             // photo
    private Long playerIdDuplicate;   // player_id (powtórzenie, jeśli potrzebne osobno)
    private String zodiac;            // zodiac

    // Statystyki – część kart, klub, dribbles, duels, fouls, games, goals, league, passes, penalty, shots, substitutes, tackles
    private Integer red;              // red
    private Integer yellow;           // yellow
    private Integer yellowRed;        // yellow_red
    private Long clubId;              // club_id
    private String clubName;          // club_name
    private Integer attempts;         // attempts (dribbles.attempts)
    private Integer past;             // past (dribbles.past)
    private Integer success;          // success (dribbles.success)
    private Integer duelsTotal;       // duels_total
    private Integer duelsWon;         // duels_won
    private Integer committed;        // committed (fouls.committed)
    private Integer drawn;            // drawn (fouls.drawn)
    private Integer appearances;      // appearances (games.appearances)
    private Boolean captain;          // captain (games.captain)
    private Integer lineups;          // lineups (games.lineups)
    private Integer minutes;          // minutes (games.minutes)
    private Integer number;           // number (games.number)
    private String position;          // position (games.position)
    private String rating;            // rating (games.rating)
    private Integer assists;          // assists (goals.assists)
    private Integer conceded;         // conceded (goals.conceded)
    private Integer goalsTotal;       // goals_total (goals.goalsTotal)
    private Integer saves;            // saves (goals.saves)
    private String countryName;       // country_name (league.countryName)
    private Long leagueId;            // league_id
    private String leagueName;        // league_name
    private String logo;              // logo (league.logo)
    private Integer season;           // season (league.season)
    private String accuracy;          // accuracy (passes.accuracy)
    private Integer key;              // key (passes.key)
    private Integer passesTotal;      // passes_total (passes.passesTotal)
    private Integer penaltiesCommitted; // penalties_committed (penalty.penaltiesCommitted)
    private Integer penaltiesMissed;    // penalties_missed (penalty.penaltiesMissed)
    private Integer penaltiesSaved;     // penalties_saved (penalty.penaltiesSaved)
    private Integer penaltiesScored;    // penalties_scored (penalty.penaltiesScored)
    private Integer penaltiesWon;       // penalties_won (penalty.penaltiesWon)
    private Integer shotsOnTarget;    // shots_on_target (shots.shotsOnTarget)
    private Integer shotsTotal;       // shots_total (shots.shotsTotal)
    private Integer bench;            // bench (substitutes.bench)
    private Integer substitutedIn;    // substituted_in (substitutes.substitutedIn)
    private Integer substitutedOut;   // substituted_out (substitutes.substitutedOut)
    private Integer blocks;           // blocks (tackles.blocks)
    private Integer interceptions;    // interceptions (tackles.interceptions)
    private Integer tacklesTotal;     // tackles_total (tackles.tacklesTotal)
    private String clubLogo;          // club_logo (club.clubLogo)
}
