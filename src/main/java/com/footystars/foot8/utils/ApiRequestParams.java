package com.footystars.foot8.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.footystars.foot8.utils.ParameterName.getFixturesParams;
import static com.footystars.foot8.utils.ParameterName.getLeaguesParams;
import static com.footystars.foot8.utils.PathSegment.API_HOST;
import static com.footystars.foot8.utils.PathSegment.FIXTURES;
import static com.footystars.foot8.utils.PathSegment.LEAGUES;

@Getter
@RequiredArgsConstructor
public enum ApiRequestParams {

    API_FIXTURE(API_HOST, API_HOST ,FIXTURES , getFixturesParams()),
    API_LEAGUES(API_HOST, API_HOST ,LEAGUES , getLeaguesParams());


    private final String scheme;
    private final String apiHost;
    private final String endpoint;
    private final List<String> params;

}
