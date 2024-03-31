package com.footystars.foot8.utils;

import lombok.Getter;

import java.util.List;

@Getter
public class ParameterNames {

    public static final String ID = "id";
    public static final String SEASON = "season";
    public static final String TEAM = "team";
    public static final String LEAGUE = "league";
    public static final String PLAYER = "player";
    public static final String COACH = "coach";
    public static final String VENUE = "venue";
    public static final String FIXTURE = "fixture";
    public static final String LAST = "last";
    public static final String NEXT = "next";
    public static final String PAGE = "page";
    public static final String BOOKMAKER = "bookmaker";
    public static final String BET = "bet";
    public static final String LIVE = "live";
    public static final String ROUND = "round";
    public static final String NAME = "name";
    public static final String TIMEZONE = "timezone";
    public static final String STATUS = "status";
    public static final String IDS = "ids";
    public static final String TYPE = "type";
    public static final String SEARCH = "search";
    public static final String CODE = "code";
    public static final String COUNTRY = "country";
    public static final String CURRENT = "current";
    public static final String CITY = "city";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String DATE = "date";

    private ParameterNames() {

    }

    public static List<String> getAllParams() {
        return List.of(ID, SEASON, TEAM, LEAGUE, PLAYER, COACH, VENUE, FIXTURE, LAST, NEXT, PAGE, NAME, TIMEZONE,
                STATUS, IDS, TYPE, SEARCH, CODE, COUNTRY, CURRENT, CITY, FROM, TO, DATE);
    }


    public static List<String> getOddsBetsParams() {
        return List.of(FIXTURE, LEAGUE, BET);

    }


    public static List<String> getCountriesParams() {
        return List.of();
    }

    public static List<String> getVenuesParams() {
        return List.of(ID, NAME, CITY, COUNTRY, SEARCH);
    }

    public static List<String> getLeaguesParams() {
        return List.of(ID, NAME, COUNTRY, CODE, SEASON, TEAM, TYPE, CURRENT, SEARCH, LAST);
    }

    public static List<String> getFixturesParams() {
        return List.of(ID, LIVE, DATE, LEAGUE, SEASON, TEAM, LAST, NEXT, FROM, TO, ROUND, TIMEZONE, STATUS, VENUE, IDS);
    }

    public static List<String> getTeamsParams() {
        return List.of(ID, NAME, LEAGUE, SEASON, COUNTRY, SEARCH);
    }

    public static List<String> getPlayersParams() {
        return List.of(ID, TEAM, LEAGUE, SEASON, SEARCH, PAGE);
    }

    public static List<String> getCoachsParams() {
        return List.of(ID, TEAM, SEARCH);
    }

    public static List<String> getTrophiesParams() {
        return List.of(PLAYER, COACH);
    }

    public static List<String> getInjuredParams() {
        return List.of(LEAGUE, SEASON, FIXTURE, TEAM, PLAYER, DATE, TIMEZONE);
    }

    public static List<String> getSidelinedParams() {
        return List.of(PLAYER, COACH);
    }

    public static List<String> getLineupsParams() {
        return List.of(TEAM, PLAYER, TYPE);
    }


    public static List<String> getTransfersParams() {
        return List.of(PLAYER, TEAM);
    }

    public static List<String> getPlayersSquadsParams() {
        return List.of(PLAYER, TEAM);
    }


    public static List<String> getTopAssistParams() {
        return List.of(LEAGUE, SEASON);
    }

    public static List<String> getTopRedCardsParams() {
        return List.of(LEAGUE, SEASON);
    }

    public static List<String> getTopYellowCardsParams() {
        return List.of(LEAGUE, SEASON);
    }

    public static List<String> getTopScorersParams() {
        return List.of(LEAGUE, SEASON);
    }

    public static List<String> getBookmakersParams() {
        return List.of(ID, SEARCH);
    }

    public static List<String> getBetParams() {
        return List.of(ID, SEARCH);
    }

    public static List<String> getOddsMappingParams() {
        return List.of(PAGE);
    }

    public static List<String> getPredictionsParams() {
        return List.of(FIXTURE);
    }

}






















