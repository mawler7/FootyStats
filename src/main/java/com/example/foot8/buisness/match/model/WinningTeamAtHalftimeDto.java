package com.example.foot8.buisness.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WinningTeamAtHalftimeDto {
    private String teamName;
    private Long totalOccurrences;
    private String lastOccurrence;
    private Long homeOccurrences;
    private Long awayOccurrences;
    private Double yearlyPercentage;
}
