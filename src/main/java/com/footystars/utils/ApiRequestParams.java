package com.footystars.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.footystars.utils.ParameterName.getFixturesParams;
import static com.footystars.utils.ParameterName.getLeaguesParams;
import static com.footystars.utils.PathSegment.API_HOST;
import static com.footystars.utils.PathSegment.FIXTURES;
import static com.footystars.utils.PathSegment.LEAGUES;

@Getter
@RequiredArgsConstructor
public enum ApiRequestParams {

    API_FIXTURE(API_HOST, API_HOST ,FIXTURES , getFixturesParams()),
    API_LEAGUES(API_HOST, API_HOST ,LEAGUES , getLeaguesParams());


    private final String scheme;
    private final String apiHost;
    private final String endpoint;
    private final List<String> params;

    public static final String REQUEST_REMAINING = "x-ratelimit-requests-remaining";
    public static final String REQUEST_LIMIT = "x-ratelimit-requests-limit";


}
