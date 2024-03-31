package com.footystars.foot8.buisness.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandingsLeague {
    private List<StandingLeague> standingLeagues;
}
