package com.footystars.utils;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.footystars.utils.ParameterName.*;

/**
 * Service responsible for providing predefined parameter maps
 * used in API requests for leagues, seasons, fixtures, teams, players, coaches, and odds.
 */
@Service
@RequiredArgsConstructor
public class ParamsProvider {

    /**
     * Generates a map containing parameters for league and season.
     *
     * @param leagueId the ID of the league
     * @param season   the season year
     * @return a map containing league and season parameters
     */
    @NotNull
    public Map<String, String> getLeagueAndSeasonParamsMap(@NotNull Long leagueId, @NotNull Integer season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, leagueId.toString());
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    /**
     * Generates a map containing parameters for odds, including league, season, and bookmaker.
     *
     * @param leagueId the ID of the league
     * @param season   the season year
     * @return a map containing odds-related parameters
     */
    @NotNull
    public Map<String, String> getOddsParamsMap(@NotNull Long leagueId, @NotNull Integer season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, leagueId.toString());
        params.put(SEASON, String.valueOf(season));
        params.put(BOOKMAKER, BET365);
        return params;
    }

    /**
     * Generates a map containing parameters for a specific fixture.
     *
     * @param fixtureId the ID of the fixture
     * @return a map containing the fixture parameter
     */
    @NotNull
    public Map<String, String> getFixtureParamsMap(@NotNull Long fixtureId) {
        var params = new HashMap<String, String>();
        params.put(FIXTURE, fixtureId.toString());
        return params;
    }

    /**
     * Generates a map containing an ID parameter for a fixture.
     *
     * @param fixtureId the ID of the fixture
     * @return a map containing the fixture ID parameter
     */
    @NotNull
    public Map<String, String> getFixtureIdParamsMap(@NotNull Long fixtureId) {
        var params = new HashMap<String, String>();
        params.put(ID, fixtureId.toString());
        return params;
    }

    /**
     * Generates a map containing parameters for a team, league, and season.
     *
     * @param id     the team ID
     * @param league the league ID
     * @param year   the season year
     * @return a map containing team, league, and season parameters
     */
    @NotNull
    public Map<String, String> getTeamLeagueAndSeasonParamsMap(@NotNull Long id, @NotNull Long league, @NotNull Integer year) {
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(id));
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(year));
        return params;
    }

    /**
     * Generates a map containing a parameter for a specific player.
     *
     * @param playerId the ID of the player
     * @return a map containing the player parameter
     */
    @NotNull
    public Map<String, String> getPlayerParams(@NotNull Long playerId) {
        var params = new HashMap<String, String>();
        params.put(PLAYER, String.valueOf(playerId));
        return params;
    }

    /**
     * Generates a map containing a parameter for a specific coach.
     *
     * @param coachId the ID of the coach
     * @return a map containing the coach parameter
     */
    @NotNull
    public Map<String, String> getCoachParams(@NotNull Long coachId) {
        var params = new HashMap<String, String>();
        params.put(COACH, String.valueOf(coachId));
        return params;
    }
}
