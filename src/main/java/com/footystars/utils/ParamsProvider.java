package com.footystars.utils;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.footystars.utils.ParameterName.BET365;
import static com.footystars.utils.ParameterName.BOOKMAKER;
import static com.footystars.utils.ParameterName.COACH;
import static com.footystars.utils.ParameterName.FIXTURE;
import static com.footystars.utils.ParameterName.ID;
import static com.footystars.utils.ParameterName.LEAGUE;
import static com.footystars.utils.ParameterName.PLAYER;
import static com.footystars.utils.ParameterName.SEASON;
import static com.footystars.utils.ParameterName.TEAM;

@Service
@RequiredArgsConstructor
public class ParamsProvider {

    @NotNull
    public Map<String, String> getLeagueAndSeasonParamsMap(@NotNull Long leagueId, @NotNull Integer season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, leagueId.toString());
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    @NotNull
    public Map<String, String> getOddsParamsMap(@NotNull Long leagueId, @NotNull Integer season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, leagueId.toString());
        params.put(SEASON, String.valueOf(season));
        params.put(BOOKMAKER, BET365);
        return params;
    }

    @NotNull
    public Map<String, String> getFixtureParamsMap(@NotNull Long fixtureId) {
        var params = new HashMap<String, String>();
        params.put(FIXTURE, fixtureId.toString());
        return params;
    }

    @NotNull
    public Map<String, String> getFixtureIdParamsMap(@NotNull Long fixtureId) {
        var params = new HashMap<String, String>();
        params.put(ID, fixtureId.toString());
        return params;
    }

    @NotNull
    public Map<String, String> getTeamLeagueAndSeasonParamsMap(@NotNull Long id, @NotNull Long league, @NotNull Integer year) {
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(id));
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(year));
        return params;
    }

    @NotNull
    public Map<String, String> getTeamParams(@NotNull Long teamId) {
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(teamId));
        return params;
    }

    @NotNull
    public Map<String, String> getPlayerParams(@NotNull Long playerId) {
        var params = new HashMap<String, String>();
        params.put(PLAYER, String.valueOf(playerId));
        return params;
    }

    @NotNull
    public Map<String, String> getCoachParams(@NotNull Long coach) {
        var params = new HashMap<String, String>();
        params.put(COACH, String.valueOf(coach));
        return params;
    }

    @NotNull
    public Map<String, String> getTeamAndSeasonParamsMap(Long clubId, int year) {
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(clubId));
        params.put(SEASON, String.valueOf(year));
        return params;
    }
}
