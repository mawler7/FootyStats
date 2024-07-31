package com.footystars.foot8.utils;

import lombok.Getter;

import java.util.List;

@Getter
public class ParameterName {

    public static final String ID = "id";
    public static final String SEASON = "season";
    public static final String TEAM = "team";
    public static final String LEAGUE = "league";
    public static final String CUP = "cup";
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
    public static final String WORLD = "World";
    public static final String PREMIER_LEAGUE = "Premier League";
    public static final String LIGUE_1 = "Ligue 1";
    public static final String SERIE_A = "Serie A";
    public static final String BUNDESLIGA = "Bundesliga";
    public static final String LA_LIGA = "La Liga";

    public static final String UEFA_SUPER_CUP = "UEFA Super Cup";
    public static final String UEFA_CHAMPIONS_LEAGUE = "UEFA Champions League";
    public static final String UEFA_EUROPA_LEAGUE = "UEFA Europa League";
    public static final String UEFA_EUROPA_CONFERENCE_LEAGUE = "UEFA Europa Conference League";
    public static final String UEFA_EUROPEAN_CHAMPIONSHIP = "UEFA European Championship";

    public static final String COMMUNITY_SHIELD = "Community Shield";
    public static final String COPPA_ITALIA = "Coppa Italia";
    public static final String COPA_DEL_REY = "Copa del Rey";
    public static final String COUPE_DE_FRANCE = "Coupe de France";
    public static final String DFB_POKAL = "DFB Pokal";
    public static final String EFL_TROPHY = "EFL Trophy";
    public static final String FA_CUP = "FA Cup";
    public static final String FA_TROPHY = "FA Trophy";
    public static final String PREMIER_LEAGUE_CUP = "Premier League Cup";
    public static final String SUPER_CUP_GERMANY = "Super Cup";
    public static final String SUPER_CUP_ITALY = "Super Cup";
    public static final String SUPER_CUP_PRIMAVERA = "Super Cup Primavera";
    public static final String TROPHEE_DES_CHAMPIONS = "Trophée des Champions";
    private ParameterName() {
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


    public static List<String> getBookmakersParams() {
        return List.of(ID, SEARCH);
    }

    public static List<String> getBetParams() {
        return List.of(ID, SEARCH);
    }

    public static List<String> getOddsMappingParams() {
        return List.of(PAGE);
    }

    public static String getPredictionsParam() {
        return FIXTURE;
    }

}






















