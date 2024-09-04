package com.footystars.utils;

import lombok.Getter;

import java.util.List;

@Getter
public class ParameterName {

    public static final String ID = "id";
    public static final String SEASON = "season";
    public static final String BOOKMAKER = "bookmaker";
    public static final String BET365 = "8";
    public static final String TEAM = "team";
    public static final String LEAGUE = "league";
    public static final String CUP = "cup";
    public static final String PLAYER = "player";
    public static final String COACH = "coach";
    public static final String VENUE = "venue";
    public static final String FIXTURE = "fixture";
    public static final String LAST = "last";
    public static final String NEXT = "next";
    public static final String PAGE = "page";
    public static final String CLIENT_ERROR = "Client error: {}";
    public static final String SERVER_ERROR = "Server error: {}";
    public static final String UNEXPECTED_STATUS_CODE = "Unexpected status code: {}";
    public static final String COULD_NOT_CREATE_REQUEST = "Could not create request";
    public static final String BET = "bet";
    public static final String LIVE = "live";
    public static final String ROUND = "round";
    public static final String NAME = "name";
    public static final String TIMEZONE = "timezone";
    public static final String STATUS = "status";
    public static final String IDS = "ids";
    public static final String TYPE = "type";
    public static final String SEARCH = "search";
    public static final String CODE = "code";
    public static final String COUNTRY = "country";
    public static final String CURRENT = "current";
    public static final String CITY = "city";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String DATE = "date";

    public static final String WORLD = "World";
    public static final String PREMIER_LEAGUE = "Premier League";
    public static final String LIGUE_1 = "Ligue 1";
    public static final String SERIE_A = "Serie A";
    public static final String BUNDESLIGA = "Bundesliga";
    public static final String LA_LIGA = "La Liga";

    public static final String UEFA_SUPER_CUP = "UEFA Super Cup";
    public static final String UEFA_CHAMPIONS_LEAGUE = "UEFA Champions League";
    public static final String UEFA_EUROPA_LEAGUE = "UEFA Europa League";

    public static final String COMMUNITY_SHIELD = "Community Shield";
    public static final String COPPA_ITALIA = "Coppa Italia";
    public static final String COPA_DEL_REY = "Copa del Rey";
    public static final String COUPE_DE_FRANCE = "Coupe de France";
    public static final String DFB_POKAL = "DFB Pokal";
    public static final String EFL_TROPHY = "EFL Trophy";
    public static final String FA_CUP = "FA Cup";
    public static final String FA_TROPHY = "FA Trophy";
    public static final String PREMIER_LEAGUE_CUP = "Premier League Cup";
    public static final String SUPER_CUP_GERMANY = "Super Cup";
    public static final String SUPER_CUP_ITALY = "Super Cup";
    public static final String SUPER_CUP_PRIMAVERA = "Super Cup Primavera";
    public static final String TROPHEE_DES_CHAMPIONS = "Trophée des Champions";

    // Add new leagues
    public static final String EKSTRAKLASA = "Ekstraklasa";
    public static final String SUPERLIGA = "Superliga";
    public static final String SUPER_LEAGUE = "Super League";
    public static final String WORLD_CUP = "World Cup";
    public static final String EURO_CHAMPIONSHIP = "Euro Championship";
    public static final String UEFA_NATIONS_LEAGUE = "UEFA Nations League";
    public static final String AFRICA_CUP_OF_NATIONS = "Africa Cup of Nations";
    public static final String FRIENDLIES = "Friendlies";
    public static final String FIFA_CLUB_WORLD_CUP = "FIFA Club World Cup";
    public static final String AFRICAN_NATIONS_CHAMPIONSHIP = "African Nations Championship";
    public static final String INTERNATIONAL_CHAMPIONS_CUP = "International Champions Cup";
    public static final String WORLD_CUP_QUALIFICATION_AFRICA = "World Cup - Qualification Africa";
    public static final String WORLD_CUP_QUALIFICATION_ASIA = "World Cup - Qualification Asia";
    public static final String WORLD_CUP_QUALIFICATION_CONCACAF = "World Cup - Qualification CONCACAF";
    public static final String WORLD_CUP_QUALIFICATION_EUROPE = "World Cup - Qualification Europe";
    public static final String WORLD_CUP_QUALIFICATION_OCEANIA = "World Cup - Qualification Oceania";
    public static final String WORLD_CUP_QUALIFICATION_SOUTH_AMERICA = "World Cup - Qualification South America";
    public static final String ASIAN_CUP_QUALIFICATION = "Asian Cup - Qualification";
    public static final String AFRICA_CUP_OF_NATIONS_QUALIFICATION = "Africa Cup of Nations - Qualification";
    public static final String WORLD_CUP_QUALIFICATION_INTERCONTINENTAL_PLAY_OFFS = "World Cup - Qualification Intercontinental Play-offs";
    public static final String LEAGUE_CUP = "League Cup";
    public static final String COUPE_DE_LA_LIGUE = "Coupe de la Ligue";
    public static final String EREDIVISIE = "Eredivisie";
    public static final String KNVB_BEKER = "KNVB Beker";
    public static final String PRIMEIRA_LIGA = "Primeira Liga";
    public static final String TACA_DE_PORTUGAL = "Taça de Portugal";
    public static final String TACA_DA_LIGA = "Taça da Liga";
    public static final String CUP_POLAND = "Cup";
    public static final String DBU_POKALEN = "DBU Pokalen";
    public static final String SUPER_LIG = "Süper Lig";
    public static final String CUP_TURKEY = "Cup";
    public static final String SCHWEIZER_CUP = "Schweizer Cup";
    public static final String MAJOR_LEAGUE_SOCCER = "Major League Soccer";
    public static final String US_OPEN_CUP = "US Open Cup";
    public static final String OLYMPICS_MEN = "Olympics Men";
    public static final String SUPER_CUP_NETHERLANDS = "Super Cup Netherlands";
    public static final String SUPER_CUP_POLAND = "Super Cup Poland";
    public static final String CONMEBOL_UEFA_FINALISSIMA = "CONMEBOL - UEFA Finalissima";
    public static final String EMIRATES_CUP = "Emirates Cup";
    public static final String UEFA_CONFERENCE_LEAGUE = "UEFA Conference League";
    public static final String KINGS_CUP = "King's Cup";

   public static final String GOAL_SCORER = "Anytime Goal Scorer";
   public static final String BOTH_TEAMS_SCORE = "Both Teams Score";
   public static final String BOTH_TEAMS_TO_SCORE_IN_BOTH_HALVES = "Both Teams To Score in Both Halves";
   public static final String DOUBLE_CHANCE = "Double Chance";
   public static final String EXACT_SCORE = "Exact Score";
   public static final String HALF_WINNER = "First Half Winner";
   public static final String OVER_UNDER = "Goals Over/Under";
   public static final String HOME_AWAY = "Home/Away";
   public static final String RESULT_GOALS = "Result/Total Goals";
   public static final String RESULTS_BOTH_TEAMS_SCORE = "Results/Both Teams Score";
   public static final String TOTAL_AWAY  = "Total - Away";
   public static final String TOTAL_HOME = "Total - Home";


    private ParameterName() {
    }

    public static List<String> getAllBets() {
        return List.of(
                GOAL_SCORER,
                BOTH_TEAMS_SCORE,
                BOTH_TEAMS_TO_SCORE_IN_BOTH_HALVES,
                DOUBLE_CHANCE,
                EXACT_SCORE,
                HALF_WINNER,
                OVER_UNDER,
                HOME_AWAY,
                RESULT_GOALS,
                RESULTS_BOTH_TEAMS_SCORE,
                TOTAL_AWAY,
                TOTAL_HOME
        );
    }
    public static List<String> getAllParams() {
        return List.of(ID, SEASON, TEAM, LEAGUE, PLAYER, COACH, VENUE, FIXTURE, LAST, NEXT, PAGE, NAME, TIMEZONE, STATUS, IDS, TYPE, SEARCH, CODE, COUNTRY, CURRENT, CITY, FROM, TO, DATE);
    }

    public static List<String> getLeaguesParams() {
        return List.of(ID, NAME, COUNTRY, CODE, SEASON, TEAM, TYPE, CURRENT, SEARCH, LAST);
    }

    public static List<String> getFixturesParams() {
        return List.of(ID, LIVE, DATE, LEAGUE, SEASON, TEAM, LAST, NEXT, FROM, TO, ROUND, TIMEZONE, STATUS, VENUE, IDS);
    }

}
