package com.footystars.foot8.utils;

import lombok.Getter;


@Getter
public class PathSegment {

    public static final String SCHEME = "https";
    public static final String API_HOST = "api-football-v1.p.rapidapi.com";
    public static final String RAPID_API_HOST_HEADER = "X-RapidAPI-Host";
    public static final String RAPID_API_KEY_HEADER = "X-RapidAPI-Key";
    public static final String TIMEZONE = "v3/timezone";
    public static final String PREDICTIONS = "v3/predictions";
    public static final String COUNTRIES = "v3/countries";
    public static final String SEASONS = "v3/leagues/seasons";
    /**
     * Optional parameters:
     * -id (NUMBER) => The id of the fixture
     * -live (STRING) => Enum: all or id-id for filter by league id
     * -date (DATE) => (YYYY-MM-DD) => A valid date, e.g., 2021-01-29
     * -league (NUMBER) => The id of the league
     * -season (NUMBER) => The season of the league => 4 characters like 2020; 2021 …
     * -team (NUMBER) => The id of the team
     * -last (NUMBER) => For the X last fixtures => <= 2 characters
     * -next (NUMBER) => For the X next fixtures => <= 2 characters
     * -from (DATE) => (YYYY-MM-DD) => A valid date, e.g., 2024-02-09
     * -to (DATE) => (YYYY-MM-DD) => A valid date, e.g., 2024-02-09
     * -round (STRING) => The round of the fixture
     * -timezone (STRING) => A valid timezone from the endpoint Timezone
     * -status (STRING) => One or more fixture status short => Like NS or NS-FT-CANC
     * -venue (NUMBER) => The venue id of the fixture
     * -ids (STRING) => One or more fixture ids => id-id-id => Maximum of 20 fixture ids
     */


    public static final String FIXTURES = "v3/fixtures";
    public static final String FIXTURES_H2H = "v3/fixtures/headtohead";
    public static final String FIXTURES_ROUNDS = "v3/fixtures/rounds";
    public static final String FIXTURES_EVENTS = "v3/fixtures/events";
    public static final String FIXTURES_STATISTICS = "v3/fixtures/statistics";
    /**
     * Required parameters:
     * -fixture (NUMBER) => The id of the fixture
     * Optional parameters:
     * -team NUMBER => The id of the team
     * -player NUMBER => The id of the player
     * -type STRING => The Lineup type Like Formation, Substitutes
     */
    public static final String FIXTURES_LINEUPS = "v3/fixtures/lineups";
    public static final String STANDINGS = "v3/standings";
    /**
     * Optional parameters:
     * -fixture (NUMBER) => The id of the fixture
     * -league (NUMBER) => The id of the league
     * -season (NUMBER) => The season of the league 4 characters Like 2020, 2021 …
     * -date DATE (YYYY-MM-DD) 2024-02-09 => A valid date
     * -timezone (STRING) => A valid timezone from the endpoint Timezone
     * -page (NUMBER) => Use for the pagination Default: 1
     * -bookmaker (NUMBER) => The id of the bookmaker
     * -bet (NUMBER) => The id of the bet
     */
    public static final String ODDS = "v3/odds";
    public static final String BETS = "v3/odds/bets";
    public static final String BOOKMAKERS = "v3/odds/bookmakers";
    public static final String ODDS_MAPPING = "v3/odds/mapping";
    public static final String ODDS_LIVE = "v3/odds/live";
    public static final String ODDS_BETS_IN_PLAY = "v3/odds/live/bets";
    public static final String PLAYERS = "v3/players";
    public static final String PLAYERS_SEASONS = "v3/players/seasons";
    public static final String PLAYERS_SQUADS = "v3/players/squads";
    /**
     * Required parameters:
     * -league (NUMBER) REQUIRED => The id of the league
     * -season (NUMBER) REQUIRED => The season of the league 4 characters ex. 2023
     */
    public static final String TOP_YELLOW_CARDS = "v3/players/topyellowcards";
    /**
     * Required parameters:
     * -league (NUMBER) REQUIRED => The id of the league
     * -season (NUMBER) REQUIRED => The season of the league 4 characters ex. 2023
     */
    public static final String TOP_RED_CARDS = "v3/players/topredcards";
    /**
     * Required parameters:
     * -league (NUMBER) REQUIRED => The id of the league
     * -season (NUMBER) REQUIRED => The season of the league 4 characters ex. 2023
     */
    public static final String TOP_SCORERS = "v3/players/topscorers";
    /**
     * Required parameters:
     * -league (NUMBER) REQUIRED => The id of the league
     * -season (NUMBER) REQUIRED => The season of the league 4 characters ex. 2023
     */
    public static final String TOP_ASSISTS = "v3/players/topassists";
    /**
     * Optional parameters:
     * -id (NUMBER) 33 => The id of the team
     * -name (STRING) => The name of the team
     * -league (NUMBER) => The id of the league
     * -season (NUMBER) => The season of the league 4 characters Like 2019, 2020, 2021
     * -country (STRING) => The country name of the team
     * -search (STRING) => The name or the country name of the team
     * -code (STRING) => The code of the team
     * -venue (NUMBER) => The id of the venue
     */
    public static final String TEAMS_INFORMATION = "v3/teams";
    /**
     * Required Parameters
     * -league (NUMBER) The id of the league
     * -season (NUMBER) The season of the league
     * -team (NUMBER) The id of the team
     */
    public static final String TEAMS_STATISTICS = "v3/teams/statistics";
    /**
     * Required Parameters
     * -id (NUMBER) The id of the team
     */
    public static final String TEAMS_SEASONS = "v3/teams/seasons";
    public static final String TEAMS_COUNTRIES = "v3/teams/countries";
    /**
     * Optional parameters:
     * -id (NUMBER) => The id of the league
     * -name (STRING) => The name of the league
     * -country (STRING) => The country name of the league
     * -code (STRING) => The Alpha2 code of the country 2 characters Like FR, GB, IT…
     * -season (NUMBER) => The season of the league 4 characters Like 2018, 2019 etc…
     * -team (NUMBER) => The id of the team
     * -type (STRING) => The type of the league Enum: league or cup
     * -current (STRING) => The state of the league Enum: true or false
     * -search (STRING) =>  The name or the country of the league >= 3 characters
     * -last (NUMBER) => The X last leagues/cups added in the API ⇐ 2 characters
     */
    public static final String LEAGUES = "v3/leagues";
    /**
     * Optional parameters:
     * -id (NUMBER) => The id of the coach
     * -team (NUMBER) => The id of the team
     * -search (STRING) =>  The name of the coach >= 3 characters
     */
    public static final String COACHS = "v3/coachs";
    /**
     * Optional parameters:
     * -league (NUMBER) => The id of the league
     * -season (NUMBER) => The season of the league, required with league, team and player parameters
     * -fixture (NUMBER) => 686308  The id of the fixture
     * -team (NUMBER) => The id of the team
     * -player (NUMBER) => The id of the player
     * -date DATE (YYYY-MM-DD) =>  A valid date
     * -timezone (STRING) => A valid timezone from the endpoint Timezone
     */
    public static final String INJURIES = "v3/injuries";
    /**
     * Optional parameters:
     * -id NUMBER  The id of the venue
     * -name (STRING) => The name of the venue
     * -city (STRING) =>  The city of the venue
     * -country (STRING) => England  The country name of the venue
     * -search (STRING) => The name, city or the country of the venue >= 3 characters
     */
    public static final String VENUES = "v3/venues";
    /**
     * Optional parameters:
     * -team (NUMBER) => The id of the team
     * -player (NUMBER) => The id of the player
     */
    public static final String TRANSFERS = "v3/transfers";
    /**
     * Optional parameters:
     * -player (NUMBER) => The id of the player
     * -coach (NUMBER) => The id of the coach
     */
    public static final String SIDELINED = "v3/sidelined";
    /**
     * Optional parameters:
     * -player (NUMBER) => The id of the player
     * -coach (NUMBER) => The id of the coach
     */
    public static final String TROPHIES = "v3/trophies";

    private PathSegment() {

    }

//    public List<String> getAllPaths() {
//        List<String> pathSegments;
//        pathSegments = List.of(TIMEZONE, PREDICTIONS, FIXTURES, FIXTURES_H2H, FIXTURES_ROUNDS, FIXTURES_EVENTS, FIXTURES_LINEUPS,
//                FIXTURES_STATISTICS, TEAMS_INFORMATION, TEAMS_STATISTICS, TEAMS_COUNTRIES, TEAMS_SEASONS, LEAGUES, ODDS, BETS, BOOKMAKERS,
//                ODDS_MAPPING, STANDINGS, PLAYERS, PLAYERS_SEASONS, TOP_SCORERS, TOP_ASSISTS, TOP_YELLOW_CARDS, TOP_RED_CARDS,
//                TROPHIES, SIDELINED, TRANSFERS, VENUES, INJURIES, COACHS);
//        return pathSegments;
//    }


}

