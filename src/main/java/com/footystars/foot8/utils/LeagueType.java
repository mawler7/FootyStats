package com.footystars.foot8.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum LeagueType {

    CUP("cup"),
    LEAGUE("league");

    private final String name;

}