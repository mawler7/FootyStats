package com.footystars.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.footystars.persistence.entity.Player;
import jakarta.persistence.Column;
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
public class Players implements Serializable {

    private List<PlayerDto> response;
    private Paging paging;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Paging implements Serializable {
        private Integer total;
        private Integer current;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlayerDto implements Serializable {

        @JsonProperty("player")
        private PlayerInfo info;
        private List<PlayerStats> statistics;

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PlayerInfo implements Serializable {

            @JsonProperty("id")
            private Long playerId;
            private String name;
            private String firstname;
            private String lastname;
            private String nationality;
            @Transient
            private Integer age;
            @Transient
            private String height;
            @Transient
            private String weight;
            private String photo;
            private Boolean injured;

            @Embedded
            private Birth birth;

            private String zodiac;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Embeddable
            public static class Birth implements Serializable {

                @JsonProperty("date")
                private String birthDate;

                @JsonProperty("place")
                private String birthPlace;

                @JsonProperty("country")
                private String birthCountry;

            }
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @Embeddable
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlayerStats implements Serializable {

        @JsonProperty("team")
        @Embedded
        private Club club;

        @Embedded
        private League league;

        @Embedded
        private Games games;

        @Embedded
        private Substitutes substitutes;

        @Embedded
        private Shots shots;

        @Embedded
        private Goals goals;

        @Embedded
        private Passes passes;

        @Embedded
        private Tackles tackles;

        @Embedded
        private Duels duels;

        @Embedded
        private Dribbles dribbles;

        @Embedded
        private Fouls fouls;

        @Embedded
        private Cards cards;

        @Embedded
        private Penalty penalty;

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Club implements Serializable {

            @JsonProperty("id")
            private Long clubId;
            @JsonProperty("logo")
            @Transient
            private String clubLogo;
            @JsonProperty("name")
            private String clubName;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Games implements Serializable {

            @JsonProperty("appearences")
            private Integer appearances;
            private Integer lineups;
            private Integer minutes;
            private Integer number;
            private String position;
            private String rating;
            private Boolean captain;
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
        public static class Substitutes implements Serializable {
            @JsonProperty("in")
            private Integer substitutedIn;
            @JsonProperty("out")
            private Integer substitutedOut;
            private Integer bench;
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

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class League implements Serializable {

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

    }
}
