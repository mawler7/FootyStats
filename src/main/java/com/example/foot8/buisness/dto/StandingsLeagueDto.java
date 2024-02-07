package com.example.foot8.buisness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandingsLeagueDto {
    private List<StandingLeagueDto> standingLeagueDtoList;
}
