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
@Table(name = "fixture_teams_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixtureStatistic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;

    @Embedded
    private Fixtures.FixtureDto.Statistics.TeamFixture team;

    private String type;

    private String value;

}