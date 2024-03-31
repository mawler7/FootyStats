package com.footystars.foot8.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

    public enum ZodiacSign {
        AQUARIUS("Aquarius", 1, 20, 2, 18),
        PISCES("Pisces", 2, 19, 3, 20),
        ARIES("Aries", 3, 21, 4, 19),
        TAURUS("Taurus", 4, 20, 5, 20),
        GEMINI("Gemini", 5, 21, 6, 20),
        CANCER("Cancer", 6, 21, 7, 22),
        LEO("Leo", 7, 23, 8, 22),
        VIRGO("Virgo", 8, 23, 9, 22),
        LIBRA("Libra", 9, 23, 10, 22),
        SCORPIO("Scorpio", 10, 23, 11, 21),
        SAGITTARIUS("Sagittarius", 11, 22, 12, 21),
        CAPRICORN("Capricorn", 12, 22, 1, 19);

        private final String name;
        private final int startMonth;
        private final int startDay;
        private final int nextMonth;
        private final int endDay;


}
