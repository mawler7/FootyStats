package com.footystars.foot8.utils;

import lombok.Getter;

@Getter
public class LogsNames {
    private LogsNames() {
    }

    public static final String FT = "FT";
    public static final String AET = "AET";
    public static final String PEN = "PEN";
    public static final String WO = "WO";
    public static final String MATCH_FINISHED = "Match Finished";
    public static final String MATCH_CANCELLED = "Match Cancelled";

    public static final String CLUB_SEASON_FETCHED = "Fetched season: {} for club: {}";

    public static final String COACHES_FETCHED = "Coaches fetched!";
    public static final String COACH_FETCHED = "Coach: {} fetched!";
    public static final String COACHES_ERROR = "Error fetching coaches for league {}: {}";

    public static final String COMPETITION_ALREADY_EXISTS = "Competition already exists";
    public static final String COMPETITION_SEASON_FETCHED = "Fetched competition {} with season {}";
    public static final String SEASON_NOT_FOUND = "Competitions not found for leagueId {} and season {}";

    public static final String COUNTRY_SAVED = "Country {} saved";
    public static final String PLAYERS_FROM_CURRENT_SEASON_COMPLETED = "Fetching players from current season completed";
    public static final String COUNTRY_UPDATED = "Updated country: {}";

    public static final String FIXTURE_ALREADY_EXISTS = "Fixtures for league {} and season {} already exist in the database";
    public static final String FIXTURE_CREATED = "Fixture for league {} in season {} created with id: {}";
    public static final String FIXTURE_ID_UPDATED = "Updated fixtureId: {}";
    public static final String FIXTURE_FETCHING_ERROR = "Error fetching fixtures for league {} and season {}: {}";
    public static final String FIXTURE_ID_FETCHING_ERROR = "Error fetching fixture {}: {}";
    public static final String FIXTURE_SAVED = "FixtureId: {} saved";
    public static final String FIXTURE_LINEUP_FETCHED = "Fixture: {} lineups fetched";
    public static final String FIXTURE_UPDATED = "Fixture: {} updated";

    public static final String FIXTURE_FETCHED = "Fetched fixtures from leagueId {}";
    public static final String ALL_FIXTURES_FETCHED = "A L L  F I X T U R E S  F E T C H E D ";

    public static final String EVENTS_FETCHING = "Fetching events for league {} and season {}";
    public static final String EVENTS_UPDATED = "Events updated successfully!";
    public static final String EVENTS_FETCHED = "Fetched fixtures events from leagueId {} and season {}";

    public static final String LEAGUE_FETCHING = "Fetching leagueId: {} in season {}";
    public static final String LEAGUE_SEASON_FETCHED = "Fetched season: {} for league: {}";
    public static final String LINEUPS_FETCHED = "Lineups updated for fixture ID: {}";
    public static final String LINEUPS_LEAGUE_FETCHED = "Lineups updated for league ID: {} in season: {}";

    public static final String PLAYERS_FETCHING = "Fetching players from league: {} and season: {}";
    public static final String PLAYERS_SIDELINED = "Player {} sidelined for league {} season {}";
    public static final String PLAYERS_FETCHED = "Players from league: {} and season: {} F E T C H E D  S U C C E S S F U L L Y ";
    public static final String FETCHED_SUCCESSFULLY = "F E T C H E D  S U C C E S S F U L L Y ";
    public static final String PLAYER_STATS_UPDATED = "Updated player stats for player: {} in league {} and season {}";
    public static final String PLAYER_STATS_UPDATING = "Updating player statistics for {}";
    public static final String PLAYER_STATS_CREATED = "Created player stats for player: {} in league {} and season {}";
    public static final String PLAYER_STATS_CREATING = "Creating player statistics for {}";
    public static final String PLAYER_STATS_ERROR = "Error while fetching player statistics for {}";
    public static final String PLAYER_ERROR = "Error while creating player: {}";
    public static final String LINEUP_ERROR = "Error while creating lineup: {} with message: {}";
    public static final String PLAYER_ALREADY_EXISTS = "Player with ID {} already exists";
    public static final String PLAYER_CREATED = "Created player {}";

    public static final String TEAM_NOT_FOUND = "Couldn't find home or away team for fixtureId: {} and season {}";
    public static final String HOME_OR_AWAY_TEAM_ERROR = "Error finding team with clubId {}, leagueId {} and season {}: {}";
    public static final String TEAM_SEASON_SAVED = "Saved team {} with season {}";
    public static final String PLAYER_STATISTICS_EXCEPTION = "Could not find league statistics for league ";
    public static final String FETCHING_PAGE_PLAYERS = "Fetching players response page: {}/{} for year: {} in leagueId: {}";
    public static final String NO_PLAYERS_FOUND = "No players data found for the given league and season.";
    public static final String API_ERROR = "Error fetching data from API";
    public static final String PLAYER_ALREADY_ASSOCIATED = "Player {} is already associated with team {}";
    public static final String FIXTURE_COULD_NOT_BE_FETCHED = "Could not fetch fixture lineups from fixture: ";
    public static final String FIXTURE_EVENTS_COULD_NOT_BE_FETCHED = "Could not fetch fixture events from fixture: ";
    public static final String FIXTURE_EVENTS_FETCHED = "Fetched fixture events for fixture: {}";

    public static final String FETCHED_ALL_LEAGUES_TEAM_STATS = "Fetched all teams statistics successfully!";
    public static final String HOME_TEAM_NOT_FOUND = "Home team not found: {}";
    public static final String AWAY_TEAM_NOT_FOUND = "Away team not found: {}";
    public static final String HOME_OR_AWAY_TEAM_NOT_FOUND = "Home or away team ID is null for league fixture: {}";
    public static final String LEAGUE_FETCHED = "League {} and its seasons fetched successfully";
    public static final String PREDICTION_ALREADY_EXISTS = "Prediction already exists for fixtureId {}";
    public static final String COULD_NOT_FETCH_PREDICTIONS = "Could not fetch prediction";
    public static final String TEAM_SAVING_ERROR = "Error saving team {}: {}";
    public static final String FETCH_CLUBS_ERROR = "Error while fetching clubs: ";
    public static final String TEAM_UPDATED = "Team: {} updated successfully!";
    public static final String TEAM_STATS_UPDATED = "Team stats updated for team {} in league {} in season {}";
    public static final String TEAM_NOT_FOUND_EXCEPTION = "Team with clubId {}, leagueId {} and season {} not found";
    public static final String TEAM_SAVED = "Tean: {} saved successfully!";
    public static final String NO_SEASON_ERROR = "No season found for leagueId {} and seasonYear {}";
    public static final String TEAM_STATS_CREATED = "Team stats created for team {} in league {} in season {}";
    public static final String TEAM_STATS_ERROR = "Error creating team stats for team {} in league {} in season {}: {}";
    public static final String INVALID_DATE = "Invalid date";
    public static final String INVALID_DATE_FORMAT = "Invalid date format: ";

    // New log messages
    public static final String API_CALLS_TODAY = "API calls today: {}";
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
    public static final String FIXTURE_NOT_FOUND = "Fixture not found with id: {}";
    public static final String TEAM_STATS_FETCH_ERROR =  "Could not fetch team statistics with message " ;
    public static final String NO_TEAMS_FOUND =  "No teams found for league {}";
    public static final String TEAM_STATS_ERROR_BY_LEAGUE_ID =  "Could not fetch team statistics for teams from league id: {} with error: {}";
}
