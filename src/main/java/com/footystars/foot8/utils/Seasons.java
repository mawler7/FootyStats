package com.footystars.foot8.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Seasons {

    _2010(2010),
    _2011(2011),
    _2012(2012),
    _2013(2013),
    _2014(2014),
    _2015(2015),
    _2016(2016),
    _2017(2017),
    _2018(2018),
    _2019(2019),
    _2020(2020),
    _2021(2021),
    _2022(2022),
    _2023(2023),
    _2024(2024),
    _2025(2025),
    _2026(2026),
    _2027(2027),
    _2028(2028),
    _2029(2029),
    _2030(2030),
    _2031(2031),
    _2032(2032);

    private final Integer year;

    public static List<Integer> getAllSeasons() {
        return Arrays.stream(Seasons.values()).map(Seasons::getYear).collect(Collectors.toList());
    }
}
