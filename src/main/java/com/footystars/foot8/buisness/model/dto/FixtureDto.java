package com.footystars.foot8.buisness.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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
    private VenueDto venue;
    private ClubDto homeTeam;
    private ClubDto awayTeam;
    private List<BetDto> bets;
    private LeagueDto league;

}