package com.footystars.foot8.persistence.entities.fixtures;

import com.footystars.foot8.persistence.entities.fixtures.events.FixtureEvent;
import com.footystars.foot8.persistence.entities.fixtures.events.FixtureStatistics;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fixtures")
public class Fixture implements Serializable {
    //fixture
    @Id
    private Long id;
    private String referee;
    private String timezone;
    private String date;
    private Long timestamp;
    private Long firstPeriod;
    private Long secondPeriod;
    //venue
    private Long venueId;
    private String venueName;
    private String venueCity;
    //status
    private String longName;
    private String shortName;
    private String elapsed;
    //league
    private Long leagueId;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private Integer season;
    private String round;
    //teams
    private Long homeTeamId;
    private String homeTeamName;
    private String homeTeamLogo;
    private Boolean homeTeamWinner;
    private Long awayTeamId;
    private String awayTeamName;
    private String awayTeamLogo;
    private Boolean awayTeamWinner;

    private Integer home;
    private Integer away;
    private Integer halfTimeHome;
    private Integer halfTimeAway;
    private Integer extraTimeHome;
    private Integer extraTimeAway;
    private Integer penaltyHome;
    private Integer penaltyAway;

    @ElementCollection
    @CollectionTable(name = "fixture_statistics", joinColumns = @JoinColumn(name = "fixture_statistics_id"))
    private List<FixtureStatistics> statistics;


    @ElementCollection
    @CollectionTable(name = "fixture_events", joinColumns = @JoinColumn(name = "fixture_event_id"))
    private List<FixtureEvent> events;

}
