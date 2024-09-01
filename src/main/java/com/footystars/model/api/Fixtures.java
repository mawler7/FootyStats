package com.footystars.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fixtures implements Serializable {

    @JsonProperty("response")
    private List<FixtureDto> fixturesList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FixtureDto implements Serializable {
        @JsonProperty("fixture")
        private FixtureInfo info;
        private LeagueDto league;
        private List<Lineup> lineups;
        private Teams teams;
        private Goals goals;
        private Score score;
        private List<FixtureEvent> events;
        private List<Statistics.Statistic> statistics;
        @JsonProperty("players")
        private List<FixturePlayers> teamPlayers;
        private ZonedDateTime lastUpdated;
        private Predictions.PredictionDto prediction;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FixtureInfo implements Serializable {
            @JsonProperty("id")
            @Transient
            @Column(insertable = false, updatable = false)
            private Long fixtureId;
            private String referee;
            private ZonedDateTime date;
            @Embedded
            private Status status;
            @Embedded
            private VenueDto venue;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Status implements Serializable {
                @JsonProperty("short")
                private String shortStatus;
                private String elapsed;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class VenueDto implements Serializable {

                @JsonProperty("name")
                private String venueName;

                @JsonProperty("city")
                private String venueCity;
            }
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class LeagueDto implements Serializable {

            @JsonProperty("id")
            private Long leagueId;

            @JsonProperty("country")
            private String countryName;

            @Transient
            private String flag;

            @JsonProperty("name")
            private String leagueName;

            private String logo;
            @Transient
            private String type;
            private Integer season;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SeasonDto implements Serializable {
            private int year;
            private String startDate;
            private String endDate;
            private Boolean current;
            @Embedded
            private Coverage coverage;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @Embeddable
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Coverage implements Serializable {

                @Embedded
                private FixturesSeason fixtures;

                private boolean standings;
                private boolean players;

                @JsonProperty("top_scorers")
                private boolean topScorers;

                @JsonProperty("top_assists")
                private boolean topAssists;

                @JsonProperty("top_cards")
                private boolean topCards;

                private boolean injuries;
                private boolean predictions;
                private boolean odds;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @Embeddable
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class FixturesSeason implements Serializable {
                    private boolean events;
                    private boolean lineups;
                    @JsonProperty("statistics_fixtures")
                    private boolean statisticsFixtures;
                    @JsonProperty("statistics_players")
                    private boolean statisticsPlayers;
                }
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Teams implements Serializable {
            @JsonProperty("home")
            @Embedded
            private HomeTeamDto homeTeam;

            @JsonProperty("away")
            @Embedded
            private AwayTeamDto awayTeam;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class HomeTeamDto implements Serializable {
                @JsonProperty("id")
                private Long homeId;
                @JsonProperty("name")
                private String homeName;
                @Transient
                @JsonProperty("logo")
                private String homeLogo;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class AwayTeamDto implements Serializable {
                @JsonProperty("id")
                private Long awayId;
                @JsonProperty("name")
                private String awayName;
                @Transient
                @JsonProperty("logo")
                private String awayLogo;
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Score implements Serializable {

            @Embedded
            private HalfTime halftime;

            @Embedded
            private FullTime fulltime;

            @Embedded
            private ExtraTime extraTime;

            @Embedded
            private Penalty penalty;


            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class FullTime implements Serializable {

                @JsonProperty("home")
                private Integer fullTimeHome;

                @JsonProperty("away")
                private Integer fullTimeAway;

            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            public static class ExtraTime implements Serializable {

                @JsonProperty("home")
                private Integer extraTimeHome;

                @JsonProperty("away")
                private Integer extraTimeAway;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class HalfTime implements Serializable {

                @JsonProperty("home")
                private Integer halfTimeHome;

                @JsonProperty("away")
                private Integer halfTimeAway;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Penalty implements Serializable {

                @JsonProperty("home")
                private Integer penaltiesHome;

                @JsonProperty("away")
                private Integer penaltiesAway;
            }


        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Goals implements Serializable {
            private Integer home;
            private Integer away;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Statistics implements Serializable {

            @JsonProperty("statistics")
            @Embedded
            private Statistic stats;
            @Embedded
            private TeamFixture team;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Embeddable
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class TeamFixture implements Serializable {
                @JsonProperty("id")
                private Long teamId;
                private String logo;
                private String name;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Statistic implements Serializable {
                @Embedded
                private Statistics.TeamFixture team;
                @JsonProperty("statistics")
                private List<StatisticValue> teamStats;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                @Embeddable
                public static class StatisticValue implements Serializable {
                    private String type;
                    private String value;
                }
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FixtureEvent implements Serializable {
            private String type;
            private String detail;
            private String comments;
            @Embedded
            private Time time;
            @Embedded
            private Team team;
            @Embedded
            private Player player;
            @Embedded
            private Assist assist;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Time implements Serializable {
                private int elapsed;
                private String extra;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Team implements Serializable {
                @JsonProperty("id")
                private Long teamId;
                private String name;
                private String logo;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Assist implements Serializable {
                @JsonProperty("id")
                private Long assistPlayerId;
                @JsonProperty("name")
                private String assistPlayerName;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Player implements Serializable {
                @JsonProperty("id")
                private Long playerId;
                @JsonProperty("name")
                private String playerName;
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Lineup implements Serializable {
            private String formation;
            private FixtureTeam team;
            private Set<PlayerStartXI> startXI;
            private Set<PlayerSubstitutes> substitutes;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class PlayerStartXI implements Serializable {
                @Embedded
                private PlayerStartXIDetails player;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class PlayerStartXIDetails implements Serializable {
                    private Long id;
                    private String name;
                    private Integer number;
                    private String pos;
                    private String grid;
                }
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class PlayerSubstitutes implements Serializable {
                @Embedded
                private PlayerSubstitutesDetails player;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class PlayerSubstitutesDetails implements Serializable {
                    private Long id;
                    private String name;
                    private Integer number;
                    private String pos;
                }
            }

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FixturePlayers implements Serializable {
            @JsonProperty("team")
            private FixtureTeam fixtureTeam;
            private Set<FixturePlayer> players;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FixtureTeam implements Serializable {
            @JsonProperty("id")
            private Long teamId;
            @JsonProperty("name")
            private String teamName;
            @Transient
            @JsonProperty("logo")
            private String teamLogo;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FixturePlayer implements Serializable {
            private FixturePlayerInfo player;
            @JsonProperty("statistics")
            private Set<FixturePlayerStats> stats;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class FixturePlayerInfo implements Serializable {
                @JsonProperty("id")
                private Long playerId;
                private String name;
                @Transient
                private String photo;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class FixturePlayerStats implements Serializable {
                @Embedded
                private Games games;
                private Integer offsides;
                @Embedded
                private Shots shots;
                @Embedded
                private Goals goals;
                @Embedded
                private Passes passes;
                @Embedded
                private Tackles tackles;
                @Embedded
                private Dribbles dribbles;
                @Embedded
                private Fouls fouls;
                @Embedded
                private Duels duels;
                @Embedded
                private Penalty penalty;
                @Embedded
                private Cards cards;

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Games implements Serializable {
                    private Integer minutes;
                    private Integer number;
                    private String position;
                    private String rating;
                    private Boolean captain;
                    private Boolean substitute;
                }

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                public static class Penalty implements Serializable {

                    @JsonProperty("commited")
                    private Integer penaltiesCommitted;
                    @JsonProperty("scored")
                    private Integer penaltiesScored;
                    @JsonProperty("missed")
                    private Integer penaltiesMissed;
                    @JsonProperty("saved")
                    private Integer penaltiesSaved;
                    @JsonProperty("won")
                    private Integer penaltiesWon;
                }

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Cards implements Serializable {
                    private Integer yellow;

                    @JsonProperty("yellowred")
                    private Integer yellowRed;

                    private Integer red;
                }

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Fouls implements Serializable {

                    private Integer drawn;
                    private Integer committed;
                }

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Dribbles implements Serializable {

                    private Integer attempts;
                    private Integer success;
                    private Integer past;

                }

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Duels implements Serializable {
                    @JsonProperty("total")
                    private Integer duelsTotal;
                    @JsonProperty("won")
                    private Integer duelsWon;
                }

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Tackles implements Serializable {
                    @JsonProperty("total")
                    private Integer tacklesTotal;
                    private Integer blocks;
                    private Integer interceptions;

                }


                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Shots implements Serializable {
                    @JsonProperty("on")
                    private Integer shotsOnTarget;
                    @JsonProperty("total")
                    private Integer shotsTotal;
                }

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Goals implements Serializable {
                    @JsonProperty("total")
                    private Integer goalsTotal;
                    private Integer conceded;
                    private Integer assists;
                    private Integer saves;
                }

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Passes implements Serializable {
                    @JsonProperty("total")
                    private Integer passesTotal;
                    private Integer key;
                    private String accuracy;
                }
            }
        }
    }
}



