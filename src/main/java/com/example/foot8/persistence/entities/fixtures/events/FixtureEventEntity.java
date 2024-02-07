package com.example.foot8.persistence.entities.fixtures.events;

import com.example.foot8.persistence.entities.fixtures.FixtureEntity;
import com.example.foot8.persistence.entities.players.PlayerEntity;
import com.example.foot8.persistence.entities.teams.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fixture_events")
public class FixtureEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int elapsed;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "assist_id")
    private PlayerEntity assist;

    private String type;
    private String detail;
    private String comments;

    @ManyToOne
    @JoinColumn(name = "fixture_id")
    private FixtureEntity fixture;
}