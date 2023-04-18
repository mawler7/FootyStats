package com.example.foot8.constants;


public enum League {
    PREMIER_LEAGUE("39"),
    LIGUE_1("61"),
    BUNDESLIGA("78"),
    SERIE_A("135"),
    PRIMIERA_DIVISION("140"),
    JUPITER_LEAGUE("144"),
    PKO_BP_EKSTRAKLASA("106"),
    SUPER_LIG("203"),
    SUPERLIGA("119");

    private final String id;

    League(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
