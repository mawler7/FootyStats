package com.footystars.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(name = "player_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerStats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fixture_player_id")
    private FixturePlayer fixturePlayer;

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


