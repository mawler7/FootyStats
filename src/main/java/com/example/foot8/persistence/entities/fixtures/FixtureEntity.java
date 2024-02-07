package com.example.foot8.persistence.entities.fixtures;

import com.example.foot8.persistence.entities.fixtures.events.FixtureEventEntity;
import com.example.foot8.persistence.entities.fixtures.statistics.FixtureStatisticsEntity;
import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import com.example.foot8.persistence.entities.seasons.SeasonsEntity;
import com.example.foot8.persistence.entities.teams.TeamEntity;
import com.example.foot8.persistence.entities.venues.VenueEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fixture")
public class FixtureEntity {

    @Id
    private Long id;

    private String referee;
    private String timezone;
    private LocalDateTime date;
    private int goalsHome;
    private int goalsAway;
    private String longStatus;
    private String shortStatus;
    private int elapsed;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private VenueEntity venue;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private LeagueEntity league;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private TeamEntity homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private TeamEntity awayTeam;


    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixtureStatisticsEntity> statistics;

    @OneToMany(mappedBy = "fixture", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<FixtureEventEntity> events;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private SeasonsEntity season;

}
