package com.footystars.model.entity;

import com.footystars.model.api.Fixtures;
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

@Entity
@Table(name = "fixtures_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixtureEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;

    private String type;
    private String detail;
    private String comments;

    @Embedded
    private Fixtures.FixtureDto.FixtureEvent.Time time;

    @Embedded
    private Fixtures.FixtureDto.FixtureEvent.Team team;

    @Embedded
    private Fixtures.FixtureDto.FixtureEvent.Player player;

    @Embedded
    private Fixtures.FixtureDto.FixtureEvent.Assist assist;

}
