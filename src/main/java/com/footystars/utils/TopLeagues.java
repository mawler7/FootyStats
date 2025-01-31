package com.footystars.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.footystars.utils.CountriesUtil.BELGIUM;
import static com.footystars.utils.CountriesUtil.DENMARK;
import static com.footystars.utils.CountriesUtil.ENGLAND;
import static com.footystars.utils.CountriesUtil.FRANCE;
import static com.footystars.utils.CountriesUtil.GERMANY;
import static com.footystars.utils.CountriesUtil.ITALY;
import static com.footystars.utils.CountriesUtil.NETHERLANDS;
import static com.footystars.utils.CountriesUtil.POLAND;
import static com.footystars.utils.CountriesUtil.PORTUGAL;
import static com.footystars.utils.CountriesUtil.SAUDI_ARABIA;
import static com.footystars.utils.CountriesUtil.SPAIN;
import static com.footystars.utils.CountriesUtil.SWITZERLAND;
import static com.footystars.utils.CountriesUtil.TURKEY;
import static com.footystars.utils.ParameterName.CUP;
import static com.footystars.utils.ParameterName.LEAGUE;
import static com.footystars.utils.ParameterName.WORLD;

@Getter
@RequiredArgsConstructor
public enum TopLeagues {

    PREMIER_LEAGUE(39L, ParameterName.PREMIER_LEAGUE, ENGLAND, LEAGUE),
    LIGUE_1(61L, ParameterName.LIGUE_1, FRANCE, LEAGUE),
    SERIE_A(135L, ParameterName.SERIE_A, ITALY, LEAGUE),
    LA_LIGA(140L, ParameterName.LA_LIGA, SPAIN, LEAGUE),
    BUNDESLIGA(78L, ParameterName.BUNDESLIGA, GERMANY, LEAGUE),

    PRIMEIRA_LIGA(94L, ParameterName.PRIMEIRA_LIGA, PORTUGAL, LEAGUE),
    JUPILER_PRO_LEAGUE(144L, ParameterName.LA_LIGA, BELGIUM, LEAGUE),
    EKSTRAKLASA(106L, ParameterName.EKSTRAKLASA, POLAND, LEAGUE),
    SUPERLIGA(119L, ParameterName.SUPERLIGA, DENMARK, LEAGUE),
    SUPER_LIG(203L, ParameterName.SUPER_LIG, TURKEY, LEAGUE),
    EREDIVISIE(88L, ParameterName.EREDIVISIE, NETHERLANDS, LEAGUE),
    SUPER_LEAGUE(207L, ParameterName.SUPER_LEAGUE, SWITZERLAND, LEAGUE),
    PRO_LEAGUE(307L,ParameterName.PRO_LEAGUE, SAUDI_ARABIA, LEAGUE),

    UEFA_CHAMPIONS_LEAGUE(2L, ParameterName.UEFA_CHAMPIONS_LEAGUE, WORLD, CUP),
    UEFA_EUROPA_LEAGUE(3L, ParameterName.UEFA_EUROPA_LEAGUE, WORLD, CUP),
    UEFA_CONFERENCE_LEAGUE(848L, ParameterName.UEFA_CONFERENCE_LEAGUE, WORLD, CUP),
    UEFA_SUPER_CUP(531L, ParameterName.UEFA_SUPER_CUP, WORLD, CUP),

    FIFA_CLUB_WORLD_CUP(15L, ParameterName.FIFA_CLUB_WORLD_CUP, WORLD, CUP),
    COMMUNITY_SHIELD(528L, ParameterName.COMMUNITY_SHIELD, ENGLAND, CUP),
    EFL_TROPHY(46L, ParameterName.EFL_TROPHY, ENGLAND, CUP),
    FA_TROPHY(47L, ParameterName.FA_TROPHY, ENGLAND, CUP),
    NATIONAL_LEAGUE_CUP(1156L, ParameterName.NATIONAL_LEAGUE_CUP, ENGLAND, CUP),
    PREMIER_LEAGUE_CUP(871L, ParameterName.PREMIER_LEAGUE_CUP, ENGLAND, CUP),
    FA_CUP(45L, ParameterName.FA_CUP, ENGLAND, CUP),
    LEAGUE_CUP(48L, ParameterName.LEAGUE_CUP, ENGLAND, CUP),
    COUPE_DE_FRANCE(66L, ParameterName.COUPE_DE_FRANCE, FRANCE, CUP),
    TROPHEE_DES_CHAMPIONS(526L, ParameterName.TROPHEE_DES_CHAMPIONS, FRANCE, CUP),
    COUPE_DE_LA_LIGUE(65L, ParameterName.COUPE_DE_LA_LIGUE, FRANCE, CUP),
    COPPA_ITALIA(137L, ParameterName.COPPA_ITALIA, ITALY, CUP),
    SUPER_CUP_ITALY(547L, ParameterName.SUPER_CUP_ITALY, ITALY, CUP),
    SUPER_CUP_PRIMAVERA(817L, ParameterName.SUPER_CUP_PRIMAVERA, ITALY, CUP),
    DFB_POKAL(81L, ParameterName.DFB_POKAL, GERMANY, CUP),
    SUPER_CUP_GERMANY(529L, ParameterName.SUPER_CUP_GERMANY, GERMANY, CUP),
    COPA_DEL_REY(143L, ParameterName.COPA_DEL_REY, SPAIN, CUP),
    SUPER_CUP_SPAIN(556L, ParameterName.SUPER_CUP_SPAIN, SPAIN, CUP),
    KNVB_BEKER(90L, ParameterName.KNVB_BEKER, NETHERLANDS, CUP),
    TACA_DE_PORTUGAL(96L, ParameterName.TACA_DE_PORTUGAL, PORTUGAL, CUP),
    TACA_DA_LIGA(97L, ParameterName.TACA_DA_LIGA, PORTUGAL, CUP),
    CUP_POLAND(108L, ParameterName.CUP_POLAND, POLAND, CUP),
    DBU_POKALEN(121L, ParameterName.DBU_POKALEN, DENMARK, CUP),

    CUP_TURKEY(206L, ParameterName.CUP_TURKEY, TURKEY, CUP),
    SCHWEIZER_CUP(209L, ParameterName.SCHWEIZER_CUP, SWITZERLAND, CUP),
    SUPER_CUP_NETHERLANDS(543L, ParameterName.SUPER_CUP_NETHERLANDS, NETHERLANDS, CUP),
    SUPER_CUP_POLAND(727L, ParameterName.SUPER_CUP_POLAND, POLAND, CUP),
    BELGIUM_CUP(147L, ParameterName.BELGIUM_CUP, BELGIUM, CUP),


    WORLD_CUP(1L, ParameterName.WORLD_CUP, WORLD, CUP),
    EURO_CHAMPIONSHIP(4L, ParameterName.EURO_CHAMPIONSHIP, WORLD, CUP),
    UEFA_NATIONS_LEAGUE(5L, ParameterName.UEFA_NATIONS_LEAGUE, WORLD, CUP),
    AFRICA_CUP_OF_NATIONS(6L, ParameterName.AFRICA_CUP_OF_NATIONS, WORLD, CUP),
    FRIENDLIES(10L, ParameterName.FRIENDLIES, WORLD, CUP),
    EMIRATES_CUP(937L, ParameterName.EMIRATES_CUP, WORLD, CUP),
    KINGS_CUP(1038L, ParameterName.KINGS_CUP, WORLD, CUP),
    CONMEBOL_UEFA_FINALISSIMA(913L, ParameterName.CONMEBOL_UEFA_FINALISSIMA, WORLD, CUP),
    AFRICAN_NATIONS_CHAMPIONSHIP(19L, ParameterName.AFRICAN_NATIONS_CHAMPIONSHIP, WORLD, CUP),
    INTERNATIONAL_CHAMPIONS_CUP(26L, ParameterName.INTERNATIONAL_CHAMPIONS_CUP, WORLD, CUP),
    WORLD_CUP_QUALIFICATION_AFRICA(29L, ParameterName.WORLD_CUP_QUALIFICATION_AFRICA, WORLD, CUP),
    WORLD_CUP_QUALIFICATION_ASIA(30L, ParameterName.WORLD_CUP_QUALIFICATION_ASIA, WORLD, CUP),
    WORLD_CUP_QUALIFICATION_CONCACAF(31L, ParameterName.WORLD_CUP_QUALIFICATION_CONCACAF, WORLD, CUP),
    WORLD_CUP_QUALIFICATION_EUROPE(32L, ParameterName.WORLD_CUP_QUALIFICATION_EUROPE, WORLD, CUP),
    WORLD_CUP_QUALIFICATION_OCEANIA(33L, ParameterName.WORLD_CUP_QUALIFICATION_OCEANIA, WORLD, CUP),
    WORLD_CUP_QUALIFICATION_SOUTH_AMERICA(34L, ParameterName.WORLD_CUP_QUALIFICATION_SOUTH_AMERICA, WORLD, CUP),
    ASIAN_CUP_QUALIFICATION(35L, ParameterName.ASIAN_CUP_QUALIFICATION, WORLD, CUP),
    AFRICA_CUP_OF_NATIONS_QUALIFICATION(36L, ParameterName.AFRICA_CUP_OF_NATIONS_QUALIFICATION, WORLD, CUP),
    WORLD_CUP_QUALIFICATION_INTERCONTINENTAL_PLAY_OFFS(37L, ParameterName.WORLD_CUP_QUALIFICATION_INTERCONTINENTAL_PLAY_OFFS, WORLD, CUP);


    private final Long id;
    private final String leagueName;
    private final String country;
    private final String type;

    public static List<Long> getTopLeaguesIds() {
        return Arrays.stream(TopLeagues.values()).map(TopLeagues::getId)
                .collect(Collectors.toList());
    }
}
