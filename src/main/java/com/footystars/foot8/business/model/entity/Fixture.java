package com.footystars.foot8.business.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "fixtures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Fixture implements Serializable {

    @Id
    private Long id;

    @Column(columnDefinition = "TIMESTAMPTZ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
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
    @Fetch(FetchMode.SELECT)
    private Set<FixtureEvent> events = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "fixtures_statistics", joinColumns = @JoinColumn(name = "fixture_id"))
    @Fetch(FetchMode.SELECT)
    private Set<Statistic> statistics = new HashSet<>();;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Lineup> lineups;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Bet> bets;

    @ManyToOne
    @JoinColumn(name = "prediction_id")
    private Prediction prediction;

    @Column(name = "last_updated", columnDefinition = "TIMESTAMPTZ")
    private ZonedDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = ZonedDateTime.now();
    }

}