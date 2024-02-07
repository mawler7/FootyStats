package com.example.foot8.api.league.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LeagueResponseData {
    private League league;
    private Country country;
    private List<Seasons> seasons;
}
