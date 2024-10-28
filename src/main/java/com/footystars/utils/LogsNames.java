package com.footystars.utils;

import lombok.Getter;

@Getter
public class LogsNames {

    public static final String PREDICTIONS_CHECKED = "Predictions checked" ;
    public static final String PARAMETERS = "parameters";
    public static final String BAERER = "Bearer ";
    public static final int TOKEN_LENGTH = 7;
    public static final String USERNAME_EXCEPTION = "User not found: ";
    public static final String TOKEN_REDIRECT = "http://localhost:3000?token=";
    public static final String LOGIN_URL = "http://localhost:8080/oauth/login/google" ;
    public static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email or password.";

    private LogsNames() {
    }

    //api
    public static final String LIMIT_EXCEEDED = "Request limit exceeded for clubId: {}, leagueId: {}, season: {}";
    public static final String LIMIT_EXCEEDED_COACH = "Request limit exceeded for teamId: {}";
    public static final String LIMIT_EXCEEDED_LEAGUE = "Request limit exceeded for league: {}";

    //leagues
    public static final String LIVE_FIXTURES_UPDATED = "Updated live fixtures: {}";
    public static final String LEAGUES_FETCHED = "Fetched all leagues";
    public static final String LEAGUE_BY_TYPE_FETCHING_ERROR = "Error fetching leagues by type";
    public static final String LEAGUES_FETCHING_ERROR = "Error fetching leagues";
    public static final String LEAGUE_BY_LEAGUE_ID_FETCHING_ERROR = "Error fetching leagues for leagueId: {}";
    public static final String NO_CURRENT_SEASON_FOUND = "No current season found for league {}";

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
    public static final String PLAYERES_SIDELINED_EXCEPTION = "Sidelined players exception: {}";

    //teams stats
    public static final String TEAMS_STATS_FETCHED_BY_LEAGUE_AND_SEASON = "Fetched teams stats in leagueId: {} and season {}";
    public static final String TEAMS_INFO_FETCHED_BY_LEAGUE_AND_SEASON = "Fetched teams info in leagueId: {} and season {}";
    public static final String TEAMS_STATS_FETCHED = "All teams stats fetched";
    public static final String TEAMS_INFO_FETCHED = "All teams info fetched";
    public static final String TEAMS_INFO_ERROR = "Error fetching teams info";
    public static final String TEAMS_FETCH_FAILED = "Failed to fetch teams for league {} and season {}";
    public static final String TEAMS_INFO_FETCH_ERROR = "Error fetching team information for league {} and season {}: {}";

    //predictions
    public static final String PREDICTIONS_ERROR = "Error processing prediction for fixtureId {}: {}";
    public static final String FETCHING_PREDICTIONS = "Fetching: {} fixtures predictions";
    public static final String PREDICTIONS_FETCHED = "Fetched upcoming predictions";
    public static final String PREDICTIONS_FETCHED_LEAGUE = "Fetched predictions for league: {}";

    //players
    public static final String PLAYERS_FETCHED = "Fetching players for all leagues completed";
    public static final String PLAYERS_CURRENT_FETCHED = "Fetched current season players";
    public static final String PLAYERS_FETCHED_LEAGUE_AND_SEASON = "Fetched players from league: {} and season: {}";

    public static final String FT = "FT";

    public static final String COACHES_FETCHED = "Coaches fetched!";
    public static final String COACH_BY_LEAGUE_FETCHED = "Coach by league: {} fetched!";
    public static final String COACHES_ERROR = "Error fetching coaches for league {}: {}";
    public static final String COACHES_FETCH_ERROR = "Error fetching coaches: {}";
    public static final String COACHES_BY_TEAM_ERROR = "Error fetching coaches for team {}: {}";

    public static final String FIXTURE_FETCHING_ERROR = "Error fetching fixtures for league {} and season {}: {}";
    public static final String FIXTURE_ID_FETCHING_ERROR = "Error fetching fixture {}: {}";
    public static final String FIXTURE_ERROR = "Error fetching fixture :{}";
    public static final String FIXTURES_FETCHED = "Fetched all fixtures";
    public static final String TODAY_FIXTURES_FETCHED = "Fetched today's fixtures";
    public static final String TODAY_FIXTURES_UPDATED = "Updated old fixtures";

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
