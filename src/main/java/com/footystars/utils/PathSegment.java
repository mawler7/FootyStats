package com.footystars.utils;

import lombok.Getter;

/**
 * Utility class containing constant values for API path segments and request parameters.
 * This class provides predefined API endpoints used for making requests to the football data API.
 */
@Getter
public class PathSegment {

    /**
     * The scheme used for API requests (HTTPS).
     */
    public static final String SCHEME = "https";

    /**
     * The host URL for the API-Football service.
     */
    public static final String API_HOST = "api-football-v1.p.rapidapi.com";

    /**
     * The header key used for specifying the API host in requests.
     */
    public static final String RAPID_API_HOST_HEADER = "X-RapidAPI-Host";

    /**
     * The header key used for specifying the API key in requests.
     */
    public static final String RAPID_API_KEY_HEADER = "X-RapidAPI-Key";

    /**
     * The endpoint for fetching match predictions.
     */
    public static final String PREDICTIONS = "v3/predictions";

    /**
     * The endpoint for retrieving fixtures.
     */
    public static final String FIXTURES = "v3/fixtures";

    /**
     * The endpoint for retrieving league standings.
     */
    public static final String STANDINGS = "v3/standings";

    /**
     * The endpoint for retrieving betting odds.
     */
    public static final String ODDS = "v3/odds";

    /**
     * The endpoint for retrieving player information.
     */
    public static final String PLAYERS = "v3/players";

    /**
     * The endpoint for retrieving team information.
     */
    public static final String TEAMS_INFORMATION = "v3/teams";

    /**
     * The endpoint for retrieving team statistics.
     */
    public static final String TEAMS_STATISTICS = "v3/teams/statistics";

    /**
     * The endpoint for retrieving league details.
     */
    public static final String LEAGUES = "v3/leagues";

    /**
     * The endpoint for retrieving coach information.
     */
    public static final String COACHS = "v3/coachs";

    /**
     * The endpoint for retrieving sidelined players or coaches.
     */
    public static final String SIDELINED = "v3/sidelined";

    /**
     * The endpoint for retrieving trophies won by players or coaches.
     */
    public static final String TROPHIES = "v3/trophies";

    /**
     * Private constructor to prevent instantiation.
     * This class is designed to be used statically.
     */
    private PathSegment() {
    }
}
