package com.footystars.foot8.persistence.entities.fixtures.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.persistence.entities.fixtures.events.FixtureEvent;
import com.footystars.foot8.persistence.entities.fixtures.events.FixtureEventDto;
import com.footystars.foot8.persistence.entities.fixtures.statistics.FixtureStatDto;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeasonDto;
import com.footystars.foot8.persistence.entities.venues.VenueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureDto implements Serializable {
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
    private TeamSeasonDto homeTeam;
    private TeamSeasonDto awayTeam;
    private Set<FixtureStatDto> stats;
    private Set<FixtureEventDto> events;
    private VenueDto venue;
}