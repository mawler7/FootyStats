package com.footystars.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leagues implements Serializable {

    private List<LeagueDto> response;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LeagueDto implements Serializable {

        @JsonProperty("league")
        private LeagueInfo info;
        private CountryDto country;
        private Set<SeasonDto> seasons;

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Embeddable
        public static class LeagueInfo implements Serializable {

            @JsonProperty("id")
            private Long leagueId;

            private String name;
            @Transient
            private String logo;
            private String type;
            @Transient
            private Integer season;
            @Transient
            private String round;
            @Transient
            private String countryName;
            @Transient
            private String flag;
        }


        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CountryDto implements Serializable {

            @JsonProperty("code")
            private String countryCode;

            private String name;
            private String flag;
        }


        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Embeddable
        public static class SeasonDto implements Serializable {

            private int year;

            @JsonProperty("start")
            private String startDate;

            @JsonProperty("end")
            private String endDate;

            private Boolean current;

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
                private Fixtures fixtures;

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

            }

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @Embeddable
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Fixtures implements Serializable {
                private boolean events;
                private boolean lineups;

                @JsonProperty("statistics_fixtures")
                private boolean statisticsFixtures;

                @JsonProperty("statistics_players")
                private boolean statisticsPlayers;
            }
        }
    }

}