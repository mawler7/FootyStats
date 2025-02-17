package com.footystars.model.entity;

import com.footystars.model.api.Fixtures;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fixtures_players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixturePlayer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;

    @Embedded
    @AttributeOverride(name = "playerId", column = @Column(name = "player_id"))
    private Fixtures.FixtureDto.FixturePlayer.FixturePlayerInfo player;

    @ElementCollection
    @CollectionTable(name = "fixture_players_stats", joinColumns = {@JoinColumn(name = "fixture_player_id")})
    private Set<Fixtures.FixtureDto.FixturePlayer.FixturePlayerStats> stats = new HashSet<>();

}
