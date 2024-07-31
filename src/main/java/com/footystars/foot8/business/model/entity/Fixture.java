package com.footystars.foot8.business.model.entity;

import com.footystars.foot8.api.model.fixtures.events.event.FixtureEvent;
import com.footystars.foot8.api.model.fixtures.statistics.statistic.Statistic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
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
    @Column(columnDefinition = "TIMESTAMPTZ")
    private ZonedDateTime date;
    private String referee;
    private String elapsed;
    private String status;
    private String fullStatus;
    private Integer home;
    private Integer away;
    private String city;
    private String venueName;
    private String leagueName;
    private String round;
    @Column(name = "home_PT")
    private Integer homePT;
    @Column(name = "home_ET")
    private Integer homeET;
    @Column(name = "home_HT")
    private Integer homeHT;
    @Column(name = "home_FT")
    private Integer homeFT;
    @Column(name = "away_HT")
    private Integer awayHT;
    @Column(name = "away_FT")
    private Integer awayFT;
    @Column(name = "away_ET")
    private Integer awayET;
    @Column(name = "away_PT")
    private Integer awayPT;

    @ElementCollection
    @CollectionTable(name = "fixtures_events", joinColumns = @JoinColumn(name = "event_id"))
    private List<FixtureEvent> events;

    @ElementCollection
    @CollectionTable(name = "fixtures_statistics", joinColumns = @JoinColumn(name = "fixture_id"))
    private List<Statistic> statistics;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lineup> lineups;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bet> bets;

    @ManyToOne
    @JoinColumn(name = "prediction_id")
    private Prediction prediction;
}