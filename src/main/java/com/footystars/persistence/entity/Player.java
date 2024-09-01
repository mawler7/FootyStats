package com.footystars.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.model.api.Players;
import com.footystars.model.api.Sidelined;
import com.footystars.model.api.Trophies;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "players")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @AttributeOverride(name = "playerId", column = @Column(name = "player_id"))
    private PlayerInfo info;

    @Embedded
    @AttributeOverride(name = "clubId", column = @Column(name = "club_id"))
    @AttributeOverride(name = "leagueId", column = @Column(name = "league_id"))
    @AttributeOverride(name = "season", column = @Column(name = "season"))
    private Players.PlayerStats statistics;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "players_sidelined", joinColumns = @JoinColumn(name = "player_id"))
    private Set<Sidelined.SidelinedDto> sidelined = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "players_trophies", joinColumns = @JoinColumn(name = "player_id"))
    private Set<Trophies.Trophy> trophies = new HashSet<>();


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
        private Boolean injured;
        private String photo;
        private String zodiac;
        @Embedded
        private Birth birth;
    }

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

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @Embeddable
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class League implements Serializable {

        @Transient
        private Long id;
        private String leagueName;
        private Integer season;

    }


}