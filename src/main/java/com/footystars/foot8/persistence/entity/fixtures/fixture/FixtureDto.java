package com.footystars.foot8.persistence.entity.fixtures.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.persistence.entity.bet.BetDto;
import com.footystars.foot8.persistence.entity.club.ClubDto;
import com.footystars.foot8.persistence.entity.fixtures.events.FixtureEventDto;
import com.footystars.foot8.persistence.entity.fixtures.statistics.FixtureStatDto;
import com.footystars.foot8.persistence.entity.leagues.LeagueDto;
import com.footystars.foot8.persistence.entity.venues.VenueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
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
    private Set<FixtureStatDto> statistics;
    private Set<FixtureEventDto> events;
    private VenueDto venue;
    private ClubDto homeTeam;
    private ClubDto awayTeam;
    private List<BetDto> bets;
    private LeagueDto league;

}