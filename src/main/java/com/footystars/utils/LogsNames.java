package com.footystars.utils;

import lombok.Getter;

@Getter
public class LogsNames {

    private LogsNames() {
    }

    //date patterns
    public static final String DATA_FORMAT_1 = "yyyy-MM-dd";
    public static final String DATA_FORMAT_2 = "yyyy-dd-MM";
    public static final String DATA_FORMAT_3 = "yyyy-dd-M";
    public static final String DATA_FORMAT_4 = "yyyy-M-dd";
    public static final String DATA_FORMAT_5 = "yyyy-MM-d";
    public static final String DATA_FORMAT_6 = "yyyy-M-d";

    //api
    public static final String LIMIT_EXCEEDED = "Request limit exceeded for clubId: {}, leagueId: {}, season: {}";
    public static final String LIMIT_EXCEEDED_COACH = "Request limit exceeded for teamId: {}";

    //leagues
    public static final String LEAGUES_FETCHED = "Fetched all leagues";
    public static final String LEAGUE_BY_TYPE_FETCHING_ERROR = "Error fetching leagues by type";
    public static final String LEAGUE_BY_LEAGUE_ID_FETCHING_ERROR = "Error fetching leagues for leagueId: {}";

    //standings
    public static final String STANDINGS_LEAGUE_SEASON_FETCHED = "Saved standings for league {} and season {}";
    public static final String STANDINGS_ERROR = "Error fetching standings for league {} and season {}: {}";
    public static final String STANDINGS_FETCHED_BY_ID = "Standings fetched by league id: {}";

    //trophies
    public static final String PLAYERES_TROPHIES_FETCHING = "Fetching {} players trophies";
    public static final String PLAYERES_TROPHIES_FETCHED = "Players trophies fetched";
    public static final String COACHES_TROPHIES_FETCHED = "Coaches trophies fetched";

    //sidelined
    public static final String PLAYERES_SIDELINED_FETCHED = "Players sidelined fetched";
    public static final String PLAYERES_SIDELINED_FETCHING = "Fetching {} sidelined players";

    //teams stats
    public static final String TEAMS_STATS_FETCHED_BY_LEAGUE_AND_SEASON = "Fetched teams stats in leagueId: {} and season {}";
    public static final String TEAMS_STATS_FETCHED = "All teams stats updated";

    //predictions
    public static final String PREDICTIONS_ERROR = "Error processing prediction for fixtureId {}: {}";

    //players
    public static final String PLAYERS_FETCHED = "Fetching players for all leagues completed";
    public static final String PLAYERS_CURRENT_FETCHED = "Fetched current season players";
    public static final String PLAYERS_FETCHED_LEAGUE_AND_SEASON = "Fetched players from league: {} and season: {}";

    public static final String FT = "FT";

    public static final String COACHES_FETCHED = "Coaches fetched!";
    public static final String COACH_BY_LEAGUE_FETCHED = "Coach by league: {} fetched!";
    public static final String COACHES_ERROR = "Error fetching coaches for league {}: {}";
    public static final String COACHES_BY_TEAM_ERROR = "Error fetching coaches for team {}: {}";

    public static final String FIXTURE_FETCHING_ERROR = "Error fetching fixtures for league {} and season {}: {}";
    public static final String FIXTURE_ID_FETCHING_ERROR = "Error fetching fixture {}: {}";
    public static final String FIXTURES_FETCHED = "Fetched all fixtures";

    public static final String LEAGUE_SEASON_FETCHED = "Fetched season: {} for league: {}";

    public static final String API_ERROR = "Error fetching data from API";
    public static final String FETCH_CLUBS_ERROR = "Error while fetching clubs: ";
    public static final String TEAM_NOT_FOUND_EXCEPTION = "Team with clubId {}, leagueId {} and season {} not found";
    public static final String INVALID_DATE = "Invalid date";
    public static final String INVALID_DATE_FORMAT = "Invalid date format: ";

    // New log messages
    public static final String FAILED_EXECUTE_IO_EXCEPTION = "Failed to execute request due to IO exception";
    public static final String FAILED_EXECUTE_MAX_RETRIES = "Failed to execute request after maximum retries";
    public static final String API_CALL_LIMIT_EXCEEDED = "API call limit exceeded for today";
    public static final String EXECUTING_REQUEST = "Executing request: {}";
    public static final String RECEIVED_429_RETRYING = "Received 429 Too Many Requests. Retrying after {} seconds";
    public static final String RECEIVED_429_TOO_MANY_REQUESTS = "Received 429 Too Many Requests";
    public static final String TIMEOUT_RETRYING = "Timeout while executing request. Retrying...";
    public static final String RECEIVED_429_TOO_MANY_REQUESTS_RETRYING = "Received 429 Too Many Requests. Retrying...";
    public static final String FAILED_EXECUTE_REQUEST = "Failed to execute request";
    public static final String THREAD_INTERRUPTED_RATE_LIMITING = "Thread was interrupted during rate limiting";
    public static final String RETRY_AFTER = "Retry-After";
    public static final String TOO_MANY_REQUESTS = "429 Too Many Requests";
    public static final String NO_TEAMS_FOUND = "No teams found for league {}";

}
