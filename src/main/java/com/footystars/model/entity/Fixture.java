package com.footystars.model.entity;

import com.footystars.model.api.Fixtures;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fixtures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fixture implements Serializable {

    @Id
    private Long id;

    @Column(name = "last_updated", columnDefinition = "TIMESTAMPTZ")
    private ZonedDateTime lastUpdated;

    @Embedded
    private Fixtures.FixtureDto.FixtureInfo info;
    @Embedded
    private Fixtures.FixtureDto.LeagueDto league;
    @Embedded
    private Fixtures.FixtureDto.Goals goals;
    @Embedded
    private Fixtures.FixtureDto.Score score;
    @Embedded
    private Fixtures.FixtureDto.Teams teams;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "prediction_id")
    private Prediction prediction;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Bet> bets = new ArrayList<>();

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Lineup> lineups = new ArrayList<>();

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FixturePlayer> players = new ArrayList<>();

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FixtureEvent> events = new ArrayList<>();

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FixtureStatistic> statistics = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.lastUpdated = ZonedDateTime.now();
    }

}
