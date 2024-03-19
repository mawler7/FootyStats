package com.footystars.foot8.persistence.entities.fixtures.fixture;

import com.footystars.foot8.persistence.entities.fixtures.events.FixtureEvent;
import com.footystars.foot8.persistence.entities.fixtures.statistics.FixtureStat;
import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeason;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import com.footystars.foot8.persistence.entities.venues.Venue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Integer homeHT;
    private Integer awayHT;
    private Integer homeFT;
    private Integer awayFT;
    private Integer homeET;
    private Integer awayET;
    private Integer homePT;
    private Integer awayPT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_season_id")
    private TeamSeason homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_season_id")
    private TeamSeason awayTeam;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FixtureStat> stats;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FixtureEvent> events;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Venue venue;


}
