package com.footystars.foot8.persistence.entities.teams.seasons;

import com.footystars.foot8.persistence.entities.coachs.season.CoachSeason;
import com.footystars.foot8.persistence.entities.fixtures.fixture.Fixture;
import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeason;
import com.footystars.foot8.persistence.entities.players.seaon.PlayerSeason;
import com.footystars.foot8.persistence.entities.teams.team.Team;
import com.footystars.foot8.persistence.entities.teams.statistics.TeamStats;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "team_seasons")
public class TeamSeason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "league_season_id")
    private LeagueSeason leagueSeason;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<PlayerSeason> playerSeasons;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CoachSeason> coachSeasons;

    @ManyToOne
    @JoinColumn(name = "team_stats_id")
    private TeamStats teamStats;



}