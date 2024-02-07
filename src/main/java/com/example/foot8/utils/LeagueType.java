package com.example.foot8.utils;

import lombok.Getter;


@Getter
public enum LeagueType {

    CUP("cup"), LEAGUE("league");

    private final String name;

    LeagueType(String name) {
        this.name = name;
    }
}