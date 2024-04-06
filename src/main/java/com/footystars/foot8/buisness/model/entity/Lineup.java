package com.footystars.foot8.buisness.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lineups")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class  Lineup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_club_id")
    private Club club;

    @ManyToMany
    @JoinTable(
            name = "lineups_startxi",
            joinColumns = @JoinColumn(name = "lineup_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> startXI;

    @ManyToMany
    @JoinTable(
            name = "lineups_substitutes",
            joinColumns = @JoinColumn(name = "lineup_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> substitutes;

    @ManyToOne
    @JoinColumn(name="coach_id")
    private Coach coach;

    private String formation;

    @ManyToOne
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;

}

