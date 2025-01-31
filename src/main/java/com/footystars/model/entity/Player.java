package com.footystars.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.model.api.Players;
import com.footystars.model.dto.player.PlayerTopScorerDto;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SqlResultSetMapping(
        name = "PlayerTopScorerMapping",
        classes = @ConstructorResult(
                targetClass = PlayerTopScorerDto.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "nationality", type = String.class),
                        @ColumnResult(name = "photo", type = String.class),
                        @ColumnResult(name = "clubName", type = String.class),
                        @ColumnResult(name = "clubLogo", type = String.class),
                        @ColumnResult(name = "form", type = String.class),
                        @ColumnResult(name = "position", type = String.class),
                        @ColumnResult(name = "appearances", type = Integer.class),
                        @ColumnResult(name = "goals", type = Integer.class),
                        @ColumnResult(name = "assists", type = Integer.class),
                        @ColumnResult(name = "yellowCards", type = Integer.class),
                        @ColumnResult(name = "redCards", type = Integer.class)
                }
        )
)
@Table(name = "players")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "last_updated", columnDefinition = "TIMESTAMPTZ")
    private ZonedDateTime lastUpdated;

    @Embedded
    @AttributeOverride(name = "playerId", column = @Column(name = "player_id"))
    private PlayerInfo info;

    @Embedded
    @AttributeOverride(name = "clubId", column = @Column(name = "club_id"))
    @AttributeOverride(name = "leagueId", column = @Column(name = "league_id"))
    @AttributeOverride(name = "season", column = @Column(name = "season"))
    private Players.PlayerStats statistics;

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

}