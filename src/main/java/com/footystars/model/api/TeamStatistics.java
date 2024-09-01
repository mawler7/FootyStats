package com.footystars.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamStatistics implements Serializable {

    @JsonProperty("response")
    private TeamStatsApi statistic;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Embeddable
    public static class TeamStatsApi implements Serializable {

        @Embedded
        private Biggest biggest;

        @Embedded
        private Cards cards;

        @JsonProperty("clean_sheet")
        @Embedded
        private CleanSheet cleanSheet;

        @JsonProperty("failed_to_score")
        @Embedded
        private FailedToScore failedToScore;

        @Embedded
        private Fixtures fixtures;

        private String form;

        @Embedded
        private Goals goals;

        @Embedded
        private League league;

        @ElementCollection
        @CollectionTable(name = "lineups", joinColumns = @JoinColumn(name = "team_id"))
        private List<Lineup> lineups;

        @Embedded
        private Penalty penalty;

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Biggest implements Serializable {

            @JsonProperty("goals")
            private Goals biggestGoals;
            private Loses loses;
            private Streak streak;
            private Wins wins;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Goals implements Serializable {

                @JsonProperty("for")
                private GoalsTotal goalsFor;
                private GoalsTotal against;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class GoalsTotal implements Serializable {

                    @JsonProperty("home")
                    private int home;
                    @JsonProperty("away")
                    private int away;
                }
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Loses implements Serializable {

                @JsonProperty("home")
                private int home;
                @JsonProperty("away")
                private int away;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Streak implements Serializable {

                private int draws;
                private int loses;
                private int wins;
            }


            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Wins implements Serializable {

                @JsonProperty("home")
                private int home;
                @JsonProperty("away")
                private int away;
                private String total;
            }

        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Cards implements Serializable {

            private Card red;
            private Card yellow;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Card implements Serializable {

                @JsonProperty("0-15")
                private CardStats from0To15;

                @JsonProperty("16-30")
                private CardStats from16To30;

                @JsonProperty("31-45")
                private CardStats from31To45;

                @JsonProperty("46-60")
                private CardStats from46To60;

                @JsonProperty("61-75")
                private CardStats from61To75;

                @JsonProperty("76-90")
                private CardStats from76To90;

                @JsonProperty("91-105")
                private CardStats from91To105;

                @JsonProperty("106-120")
                private CardStats from106To120;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class CardStats implements Serializable {

                    private String percentage;
                    private int total;
                }
            }

        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CleanSheet implements Serializable {

            @JsonProperty("home")
            private int home;
            @JsonProperty("away")
            private int away;
            private int total;

        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class FailedToScore implements Serializable {

            @JsonProperty("home")
            private int home;
            @JsonProperty("away")
            private int away;
            private int total;

        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Fixtures implements Serializable {

            private Draws draws;
            private Loses loses;
            private Played played;
            private Wins wins;


            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Draws implements Serializable {

                @JsonProperty("home")
                private int home;
                @JsonProperty("away")
                private int away;
                private int total;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Loses implements Serializable {

                @JsonProperty("home")
                private int home;
                @JsonProperty("away")
                private int away;
                private int total;

            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Played implements Serializable {

                @JsonProperty("home")
                private int home;
                @JsonProperty("away")
                private int away;
                private int total;

            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Wins implements Serializable {

                @JsonProperty("home")
                private int home;
                @JsonProperty("away")
                private int away;
                private int total;

            }

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @Builder
        @AllArgsConstructor
        public static class Goals implements Serializable {

            @JsonProperty("for")
            private TeamStatisticGoals goalsFor;
            private TeamStatisticGoals against;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class TeamStatisticGoals implements Serializable {

                private GoalsAverage average;
                private GoalsMinutes minute;
                private TeamStatsApi.Biggest.Goals.GoalsTotal total;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class GoalsAverage implements Serializable {

                    @JsonProperty("home")
                    private int home;
                    @JsonProperty("away")
                    private int away;
                    private String total;
                }
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class GoalsMinutes implements Serializable {

                    @JsonProperty("0-15")
                    private GoalsStat from0To15;
                    @JsonProperty("16-30")
                    private GoalsStat from16To30;
                    @JsonProperty("31-45")
                    private GoalsStat from31To45;
                    @JsonProperty("46-60")
                    private GoalsStat from46To60;
                    @JsonProperty("61-75")
                    private GoalsStat from61To75;
                    @JsonProperty("76-90")
                    private GoalsStat from76To90;
                    @JsonProperty("91-105")
                    private GoalsStat from91To105;
                    @JsonProperty("106-120")
                    private GoalsStat from106To120;

                    @NoArgsConstructor
                    @AllArgsConstructor
                    @Builder
                    @Getter
                    @Setter
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class GoalsStat implements Serializable {

                        private String percentage;
                        private Integer total;

                    }

                }


            }

        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class League implements Serializable {

            @JsonProperty("id")
            @Transient
            private Long leagueId;
            @JsonProperty("name")
            private String leagueName;
            @Transient
            private String country;
            @JsonProperty("logo")
            @Transient
            private String leagueLogo;
            @JsonProperty("season")
            private Integer year;

        }

        @Getter
        @Setter
        @RequiredArgsConstructor
        @Builder
        @AllArgsConstructor
        @Embeddable
        public static class Lineup implements Serializable {

            private String formation;
            private Long played;

        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Penalty implements Serializable {

            private PenaltyMissed missed;
            private PenaltyScored scored;
            private Long total;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class PenaltyMissed implements Serializable {

                private String percentage;
                private Long total;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class PenaltyScored implements Serializable {

                private String percentage;
                private Long total;

            }

        }

    }
}
