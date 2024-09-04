package com.footystars.model.entity;

import com.footystars.model.api.Fixtures;
import jakarta.persistence.CollectionTable;
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
@Table(name = "fixture_lineups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lineup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;

    @Embedded
    private Fixtures.FixtureDto.FixtureTeam team;

    private String formation;

    @ElementCollection
    @CollectionTable(name = "lineup_players", joinColumns = @JoinColumn(name = "lineup_id"))
    private Set<Fixtures.FixtureDto.Lineup.PlayerStartXI> startXI = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "lineup_substitutes", joinColumns = @JoinColumn(name = "lineup_id"))
    private Set<Fixtures.FixtureDto.Lineup.PlayerSubstitutes> substitutes = new HashSet<>();

}
