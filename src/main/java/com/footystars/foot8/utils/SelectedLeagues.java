package com.footystars.foot8.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.footystars.foot8.utils.LeagueType.CUP;
import static com.footystars.foot8.utils.ParameterName.LEAGUE;

@Getter
@RequiredArgsConstructor
public enum SelectedLeagues {

    PREMIER_LEAGUE(39L, "Premier League", "England", CUP.getName()),
    FA_CUP(45L, "FA Cup", "England", CUP.getName()),
    EFL_TROPHY(46L, "EFL Trophy", "England", CUP.getName()),
    FA_TROPHY(47L, "FA Trophy", "England", CUP.getName()),
    CHAMPIONSHIP(40L, "Championship", "England", CUP.getName()),
    LIGUE_1(61L, "Ligue 1", "France", LEAGUE),
    LIGUE_2(62L, "Ligue 2", "France", CUP.getName()),
    COUPE_DE_FRANCE(66L, "Coupe de France", "France", CUP.getName()),
    SERIE_A(71L, "Serie A", "Italy", CUP.getName()),
    SERIE_B(72L, "Serie B", "Italy", CUP.getName()),
    COPA_DO_BRASIL(73L, "Copa Do Brasil", "Brazil", CUP.getName()),
    BUNDESLIGA(78L, "Bundesliga", "Germany", CUP.getName()),
    SECOND_BUNDESLIGA(79L, "2. Bundesliga", "Germany", CUP.getName()),
    DFB_POKAL(81L, "DFB Pokal", "Germany", "Cup"),
    EREDIVISIE(88L, "Eredivisie", "Netherlands", CUP.getName()),
    EERSTE_DIVISIE(89L, "Eerste Divisie", "Netherlands", CUP.getName()),
    KNVB_BEKER(90L, "KNVB Beker", "Netherlands", CUP.getName()),
    PRIMEIRA_LIGA(94L, "Primeira Liga", "Portugal", CUP.getName()),
    I_LIGA(107L, "I Liga", "Poland", CUP.getName()),
    POLISH_CUP(108L, "Cup", "Poland", "Cup"),
    SUPERLIGA(119L, "Superliga", "Denmark", CUP.getName()),
    DBU_POKALEN(121L, "DBU Pokalen", "Denmark", CUP.getName()),
    TACA_DE_PORTUGAL(96L, "Taça de Portugal", "Portugal", CUP.getName()),
    COPPA_ITALIA(137L, "Coppa Italia", "Italy", CUP.getName()),
    LA_LIGA(140L, "La Liga", "Spain", CUP.getName()),
    SEGUNDA_DIVISION(141L, "Segunda División", "Spain", CUP.getName()),
    COPA_DEL_REY(143L, "Copa del Rey", "Spain", CUP.getName()),
    JUPILER_PRO_LEAGUE(144L, "Jupiler Pro League", "Belgium", CUP.getName()),
    CHALLENGER_PRO_LEAGUE(145L, "Challenger Pro League", "Belgium", CUP.getName()),
    BELGIAN_CUP(147L, "Cup", "Belgium", CUP.getName()),
    TURKISH_CUP(206L, "Cup", "Turkey", CUP.getName()),
    US_OPEN_CUP(257L, "US Open Cup", "USA", CUP.getName()),
    CUPA_ROMANIEI(285L, "Cupa României", "Romania", CUP.getName()),
    PRO_LEAGUE(307L, "Pro League", "Saudi-Arabia", CUP.getName()),
    CZECH_LIGA(345L, "Czech Liga", "Czech-Republic", CUP.getName()),
    CZECH_CUP(347L, "Cup", "Czech-Republic", "Cup"),
    TROPHEE_DES_CHAMPIONS(526L, "Trophée des Champions", "France", CUP.getName()),
    COMMUNITY_SHIELD(528L, "Community Shield", "England", CUP.getName()),
    SUPER_CUP_GERMANY(529L, "Super Cup", "Germany", CUP.getName()),
    UEFA_SUPER_CUP(531L, "UEFA Super Cup", "World", CUP.getName()),
    SUPER_CUP_NETHERLANDS(543L, "Super Cup", "Netherlands", CUP.getName()),
    SUPER_CUP_ITALY(547L, "Super Cup", "Italy", CUP.getName()),
    SUPER_CUP_POLAND(727L, "Super Cup", "Poland", CUP.getName()),
    SUPER_CUP_PRIMAVERA(817L, "Super Cup Primavera", "Italy", CUP.getName()),
    PREMIER_LEAGUE_CUP(871L, "Premier League Cup", "England", CUP.getName()),
    SUPER_CUP_CZECH_REPUBLIC(925L, "Super Cup", "Czech-Republic", CUP.getName()),
    DENMARK_SERIES_PROMOTION_ROUND(982L, "Denmark Series - Promotion Round", "Denmark", CUP.getName()),
    DENMARK_SERIES_RELEGATION_ROUND(983L, "Denmark Series - Relegation Round", "Denmark", CUP.getName());

    private final Long id;
    private final String leagueName;
    private final String country;
    private final String type;

    public static List<Long> getAllIds() {
        return Arrays.stream(SelectedLeagues.values())
                .map(SelectedLeagues::getId)
                .collect(Collectors.toList());
    }

    public static List<Long> getEuropeansTop5LeaguesIds() {
        var top5Leagues = Arrays.asList(
                PREMIER_LEAGUE.id,
                LIGUE_1.id,
                SERIE_A.id,
                BUNDESLIGA.id,
                LA_LIGA.id
        );

        return Arrays.stream(SelectedLeagues.values())
                .map(SelectedLeagues::getId)
                .filter(top5Leagues::contains)
                .collect(Collectors.toList());
    }
}






