package com.example.foot8.api.fixture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixtureDto {
    private Long id;
    private String referee;
    private String timezone;
    private LocalDateTime date;
    private Long timestamp;
    private PeriodsDto periods;
    private VenueDto venue;
    private LeagueDto league;
    private TeamsDto teams;
    private ScoreDto score;
    private StatusDto status;

}
