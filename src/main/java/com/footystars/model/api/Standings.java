package com.footystars.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Standings implements Serializable {

    private List<StandingApi> response;


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StandingApi implements Serializable {
        private StandingLeague league;

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class StandingLeague implements Serializable {
            private Long id;
            private String name;
            private String country;
            private String logo;
            private String flag;
            private Integer season;
            private ArrayList<ArrayList<Standing>> standings;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @Embeddable
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Standing implements Serializable {

                @Embedded
                private StandingTeam team;
                private Integer rank;
                private Integer points;
                private Integer goalsDiff;

                @JsonProperty("group")
                private String groupInfo;
                private String form;
                private String status;
                private String description;

                @AttributeOverride(name ="played", column = @Column(name = "played_total"))
                @AttributeOverride(name ="win", column = @Column(name = "win_total"))
                @AttributeOverride(name ="draw", column = @Column(name = "draw_total"))
                @AttributeOverride(name ="lose", column = @Column(name = "lose_total"))
                @AttributeOverride(name ="goal.goalsFor", column = @Column(name = "goals_for_total"))
                @AttributeOverride(name ="goal.goalsAgainst", column = @Column(name = "goals_against_total"))
                @Embedded
                @JsonProperty("all")
                private AllStat allStats;

                @AttributeOverride(name ="played",  column = @Column(name = "played_home"))
                @AttributeOverride(name ="win", column = @Column(name = "win_home"))
                @AttributeOverride(name ="draw", column = @Column(name = "draw_home"))
                @AttributeOverride(name ="lose", column = @Column(name = "lose_home"))
                @AttributeOverride(name ="homeGoals.goalsFor", column = @Column(name = "goals_for_home"))
                @AttributeOverride(name ="homeGoals.goalsAgainst", column = @Column(name = "goals_against_home"))
                @Embedded
                @JsonProperty("home")
                private HomeStat homeStats;

                @AttributeOverride(name ="played", column = @Column(name = "played_away"))
                @AttributeOverride(name ="win", column = @Column(name = "win_away"))
                @AttributeOverride(name ="draw", column = @Column(name = "draw_away"))
                @AttributeOverride(name ="lose", column = @Column(name = "lose_away"))
                @AttributeOverride(name ="awayGoals.goalsFor", column = @Column(name = "goals_for_away"))
                @AttributeOverride(name ="awayGoals.goalsAgainst", column = @Column(name = "goals_against_away"))
                @Embedded
                @JsonProperty("away")
                private AwayStat awayStats;

                private ZonedDateTime update;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @Embeddable
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class StandingTeam implements Serializable {
                    private Long id;
                    private String name;

                }

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @Embeddable
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class AllStat implements Serializable {

                    private Integer played;
                    private Integer win;
                    private Integer draw;
                    private Integer lose;
                    @Embedded
                    private Goals goals;

                    @NoArgsConstructor
                    @AllArgsConstructor
                    @Builder
                    @Getter
                    @Setter
                    @Embeddable
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Goals implements Serializable {

                        @JsonProperty("for")
                        private Integer goalsFor;
                        @JsonProperty("against")
                        private Integer goalsAgainst;
                    }


                }

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @Embeddable
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class HomeStat implements Serializable {

                    private Integer played;
                    private Integer win;
                    private Integer draw;
                    private Integer lose;
                    @JsonProperty("goals")
                    private AllStat.Goals homeGoals;


                }
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @Embeddable
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class AwayStat implements Serializable {

                    private Integer played;
                    private Integer win;
                    private Integer draw;
                    private Integer lose;
                    @JsonProperty("goals")
                    private AllStat.Goals awayGoals;


                }



            }



        }

    }
}