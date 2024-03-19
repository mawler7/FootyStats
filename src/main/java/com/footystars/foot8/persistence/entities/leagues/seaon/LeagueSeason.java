package com.footystars.foot8.persistence.entities.leagues.seaon;

import com.footystars.foot8.persistence.entities.teams.standings.Standing;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import com.footystars.foot8.persistence.entities.fixtures.fixture.Fixture;
import com.footystars.foot8.persistence.entities.leagues.league.League;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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


import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "league_seasons")
public class LeagueSeason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private String startDate;
    private String endDate;
    private Boolean current;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "league_id")
    private League league;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Standing> standings;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Fixture> fixtures;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TeamSeason> teamSeasons;

}