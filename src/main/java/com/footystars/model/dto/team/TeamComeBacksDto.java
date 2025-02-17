package com.footystars.model.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamComeBacksDto {
    private String logo;
    private String team;
    private int draws;
    private int totalMatches;
    private ZonedDateTime lastDrawDate;
}