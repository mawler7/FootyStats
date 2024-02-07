package com.example.foot8.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum League {
    ENGLAND("39", "Premier League"),
    FRANCE("61", "Ligue 1"),
    GERMANY("78", "Bundesliga"),
    ITALY("135", "Serie A"),
    SPAIN("140", "La Liga"),
    BELGIUM("144", "Jupiler Pro League"),
    POLAND("106", "Ekstraklasa"),
    TURKEY("203", "Süper Lig"),
    DENMARK("119", "Superliga");

    private final String id;
    private final String leagueName;
public static List<String> getAllIds() {
    return Arrays.stream(League.values())
            .map(League::getId)
            .collect(Collectors.toList());
}
}
