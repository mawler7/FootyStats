package com.footystars.foot8.api.model.teams.statistics.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamStatsParams {
    String league;
    String season;
    String team;

}
