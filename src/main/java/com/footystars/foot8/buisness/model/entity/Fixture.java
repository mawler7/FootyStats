package com.footystars.foot8.buisness.model.entity;

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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fixtures")
public class Fixture implements Serializable {

    @Id
    private Long id;
    private String date;
    private String referee;
    private String elapsed;
    private String status;
    private String fullStatus;
    private Integer home;
    private Integer away;

    @Column(name = "home_HT")
    private Integer homeHT;

    @Column(name = "away_HT")
    private Integer awayHT;

    @Column(name = "home_FT")
    private Integer homeFT;

    @Column(name = "away_FT")
    private Integer awayFT;

    @Column(name = "home_ET")
    private Integer homeET;

    @Column(name = "away_ET")
    private Integer awayET;

    @Column(name = "home_PT")
    private Integer homePT;

    @Column(name = "away_PT")
    private Integer awayPT;

    @ElementCollection
    @CollectionTable(name = "fixtures_events", joinColumns = @JoinColumn(name = "event_id"))
    private List<FixtureEvent> events;

    @ElementCollection
    @CollectionTable(name = "fixtures_statistics", joinColumns = @JoinColumn(name = "statistic_id"))
    private List<Statistic> statistics;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Lineup> lineups;

    @ManyToMany(mappedBy = "fixtures")
    private Set<Competition> competitions;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bet> bets;

    @ManyToOne
    @JoinColumn(name = "prediction_id")
    private Prediction prediction;

}
