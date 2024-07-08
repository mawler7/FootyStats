package com.footystars.foot8.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.footystars.foot8.utils.CountriesUtil.ENGLAND;
import static com.footystars.foot8.utils.CountriesUtil.FRANCE;
import static com.footystars.foot8.utils.CountriesUtil.GERMANY;
import static com.footystars.foot8.utils.CountriesUtil.ITALY;
import static com.footystars.foot8.utils.CountriesUtil.SPAIN;
import static com.footystars.foot8.utils.ParameterName.CUP;
import static com.footystars.foot8.utils.ParameterName.LEAGUE;
import static com.footystars.foot8.utils.ParameterName.WORLD;


@Getter
@RequiredArgsConstructor
public enum SelectedLeagues {

    PREMIER_LEAGUE(39L,  ParameterName.PREMIER_LEAGUE, ENGLAND, LEAGUE),
    LIGUE_1(61L, ParameterName.LIGUE_1, FRANCE, LEAGUE),
    SERIE_A(135L, ParameterName.SERIE_A, ITALY, LEAGUE),
    BUNDESLIGA(78L, ParameterName.BUNDESLIGA, GERMANY, LEAGUE),
    LA_LIGA(140L, ParameterName.LA_LIGA, SPAIN, LEAGUE),

    UEFA_SUPER_CUP(531L, ParameterName.UEFA_SUPER_CUP, WORLD, CUP),
    UEFA_CHAMPIONS_LEAGUE(2L, ParameterName.UEFA_CHAMPIONS_LEAGUE, WORLD, CUP),
    UEFA_EUROPA_LEAGUE(3L, ParameterName.UEFA_EUROPA_LEAGUE, ENGLAND, CUP),
    UEFA_EUROPA_CONFERENCE_LEAGUE(848L, ParameterName.UEFA_EUROPA_CONFERENCE_LEAGUE, ENGLAND, CUP),

    COMMUNITY_SHIELD(528L, ParameterName.COMMUNITY_SHIELD, ENGLAND, CUP),
    COPPA_ITALIA(137L, ParameterName.COPPA_ITALIA, ITALY, CUP),
    COPA_DEL_REY(143L, ParameterName.COPA_DEL_REY, SPAIN, CUP),
    COUPE_DE_FRANCE(66L, ParameterName.COUPE_DE_FRANCE, FRANCE, CUP),
    DFB_POKAL(81L, ParameterName.DFB_POKAL, GERMANY, CUP),
    EFL_TROPHY(46L, ParameterName.EFL_TROPHY, ENGLAND, CUP),
    FA_CUP(45L, ParameterName.FA_CUP, ENGLAND, CUP),
    FA_TROPHY(47L, ParameterName.FA_TROPHY, ENGLAND, CUP),
    PREMIER_LEAGUE_CUP(871L, ParameterName.PREMIER_LEAGUE_CUP, ENGLAND, CUP),
    SUPER_CUP_GERMANY(529L, ParameterName.SUPER_CUP_GERMANY, GERMANY, CUP),
    SUPER_CUP_ITALY(547L, ParameterName.SUPER_CUP_ITALY, ITALY, CUP),
    SUPER_CUP_PRIMAVERA(817L, ParameterName.SUPER_CUP_PRIMAVERA, ITALY, CUP),
    TROPHEE_DES_CHAMPIONS(526L, ParameterName.TROPHEE_DES_CHAMPIONS, FRANCE, CUP);

    private final Long id;
    private final String leagueName;
    private final String country;
    private final String type;

    public static List<Long> getFavoritesLeaguesAndCups() {

        var topLeagues = Arrays.asList(
                PREMIER_LEAGUE.id,
                LIGUE_1.id,
                SERIE_A.id,
                BUNDESLIGA.id,
                LA_LIGA.id,

                COMMUNITY_SHIELD.id,
                COPPA_ITALIA.id,
                COPA_DEL_REY.id,
                COUPE_DE_FRANCE.id,
                DFB_POKAL.id,
                EFL_TROPHY.id,
                FA_CUP.id,
                FA_TROPHY.id,
                PREMIER_LEAGUE_CUP.id,
                SUPER_CUP_GERMANY.id,
                SUPER_CUP_ITALY.id,
                SUPER_CUP_PRIMAVERA.id,
                TROPHEE_DES_CHAMPIONS.id
        );
        return Arrays.stream(SelectedLeagues.values())
                .map(SelectedLeagues::getId)
                .filter(topLeagues::contains)
                .collect(Collectors.toList());
    }
    public static List<Long> getSelectedCups() {

        var europeanCups = Arrays.asList(
                UEFA_SUPER_CUP.id,
                UEFA_CHAMPIONS_LEAGUE.id,
                UEFA_EUROPA_LEAGUE.id,
                UEFA_EUROPA_CONFERENCE_LEAGUE.id
        );
        return Arrays.stream(SelectedLeagues.values())
                .map(SelectedLeagues::getId)
                .filter(europeanCups::contains)
                .collect(Collectors.toList());
    }
    public static List<Long> getEuropeanCups() {

        var topCups = Arrays.asList(
                UEFA_SUPER_CUP.id,
                UEFA_CHAMPIONS_LEAGUE.id,
                UEFA_EUROPA_LEAGUE.id,
                UEFA_EUROPA_CONFERENCE_LEAGUE.id
        );
        return Arrays.stream(SelectedLeagues.values())
                .map(SelectedLeagues::getId)
                .filter(topCups::contains)
                .collect(Collectors.toList());
    }
    public static List<Long> getTopLeaguesIds() {
        return Arrays.stream(SelectedLeagues.values()).map(SelectedLeagues::getId)
                .collect(Collectors.toList());
    }

}






