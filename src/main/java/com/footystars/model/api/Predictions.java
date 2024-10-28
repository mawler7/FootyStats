package com.footystars.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Predictions implements Serializable {

    @JsonProperty("response")
    private List<PredictionDto> predictionsList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PredictionDto implements Serializable {
        private PredictionDetails predictions;
        private Comparison comparison;
        private Teams teams;
        private List<H2HData> h2h;

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Embeddable
        public static class PredictionDetails implements Serializable {
            @Embedded
            private Winner winner;

            @JsonProperty("win_or_draw")
            private Boolean winOrDraw;

            @JsonProperty("under_over")
            private String underOver;

            @Embedded
            private Goals goals;

            private String advice;

            @Embedded
            private Percent percent;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Goals implements Serializable {
                @JsonProperty("home")
                private String homePrediction;
                @JsonProperty("away")
                private String awayPrediction;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Percent implements Serializable {
                @JsonProperty("home")
                private String homePercentage;
                @JsonProperty("draw")
                private String drawPercentage;
                @JsonProperty("away")
                private String awayPercentage;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Winner implements Serializable {
                @JsonProperty("id")
                private Long winnerId;
                @JsonProperty("name")
                private String winnerName;
                private String comment;
            }
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Embeddable
        public static class Comparison implements Serializable {
            @Embedded
            private Form form;
            @Embedded
            private Att att;
            @Embedded
            private Def def;
            @JsonProperty("poisson_distribution")
            @Embedded
            private PoissonDistribution poissonDistribution;
            @Embedded
            private H2H h2h;
            @Embedded
            private Goals goals;
            @Embedded
            private Total total;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Form implements Serializable {

                @JsonProperty("home")
                private String homeForm;

                @JsonProperty("away")
                private String awayForm;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Att implements Serializable {

                @JsonProperty("home")
                private String homeAtt;

                @JsonProperty("away")
                private String awayAtt;

            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Def implements Serializable {

                @JsonProperty("home")
                private String homeDef;
                @JsonProperty("away")
                private String awayDef;

            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class PoissonDistribution implements Serializable {

                @JsonProperty("home")
                private String homePercentagePD;

                @JsonProperty("away")
                private String awayPercentagePD;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class H2H implements Serializable {

                @JsonProperty("home")
                private String homePercentageH2H;

                @JsonProperty("away")
                private String awayPercentageH2H;

            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Goals implements Serializable {

                @JsonProperty("home")
                private String homeGoalsComparison;

                @JsonProperty("away")
                private String awayGoalsComparison;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class Total implements Serializable {

                @JsonProperty("home")
                private String homeTotal;

                @JsonProperty("away")
                private String awayTotal;
            }

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Embeddable
        public static class Teams implements Serializable {
            @JsonProperty("home")
            @Embedded
            private HomeTeamDto homeTeam;
            @JsonProperty("away")
            @Embedded
            private AwayTeamDto awayTeam;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class HomeTeamDto implements Serializable {
                @JsonProperty("id")
                private Long homeClubId;
                @JsonProperty("name")
                private String homeName;
            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            @Embeddable
            public static class AwayTeamDto implements Serializable {
                @JsonProperty("id")
                private Long awayClubId;
                @JsonProperty("name")
                private String awayName;
            }
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class H2HData implements Serializable {
            private FixtureH2H fixture;
            private Teams teams;
            private Goals goals;
            private ScoredStats score;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class FixtureH2H implements Serializable {
                private Long id;
                private ZonedDateTime date;
                private String referee;
                private TeamsInfo.VenueDto venue;
                private Fixtures.FixtureDto.FixtureInfo.Status status;

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
                private PredictionDto.Teams.HomeTeamDto homeTeam;

                @JsonProperty("away")
                @Embedded
                private PredictionDto.Teams.AwayTeamDto awayTeam;

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

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class ScoredStats implements Serializable {
                private Integer total;
                private String percentage;
            }
        }

    }

}
