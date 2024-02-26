package com.footystars.foot8.buisness.model.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeagueDataWrapper {

    private String leagueName;
    private Long leagueId;
    private String leagueLogo;
    private Integer season;

}
