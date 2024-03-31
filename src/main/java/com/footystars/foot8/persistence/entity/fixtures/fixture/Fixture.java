package com.footystars.foot8.persistence.entity.fixtures.fixture;

import com.footystars.foot8.persistence.entity.bet.Bet;
import com.footystars.foot8.persistence.entity.club.Club;
import com.footystars.foot8.persistence.entity.competitions.Competition;
import com.footystars.foot8.persistence.entity.fixtures.events.FixtureEvent;
import com.footystars.foot8.persistence.entity.fixtures.statistics.FixtureStat;
import com.footystars.foot8.persistence.entity.venues.Venue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fixtures")
public class Fixture {

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

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FixtureEvent> events;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FixtureStat> statistics;


    @ManyToMany(mappedBy = "fixtures")
    private Set<Competition> competitions;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Club homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Club awayTeam;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bet> bets = new ArrayList<>();


}
