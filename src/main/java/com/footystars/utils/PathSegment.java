package com.footystars.utils;

import lombok.Getter;


@Getter
public class PathSegment {

    public static final String SCHEME = "https";
    public static final String API_HOST = "api-football-v1.p.rapidapi.com";
    public static final String RAPID_API_HOST_HEADER = "X-RapidAPI-Host";
    public static final String RAPID_API_KEY_HEADER = "X-RapidAPI-Key";
    public static final String PREDICTIONS = "v3/predictions";
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
    /**
     * Required parameters:
     * -fixture (NUMBER) => The id of the fixture
     * Optional parameters:
     * -team NUMBER => The id of the team
     * -player NUMBER => The id of the player
     * -type STRING => The Lineup type Like Formation, Substitutes
     */
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
    public static final String PLAYERS = "v3/players";
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


}

