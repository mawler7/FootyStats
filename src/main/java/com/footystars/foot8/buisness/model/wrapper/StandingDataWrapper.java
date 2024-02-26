package com.footystars.foot8.buisness.model.wrapper;

import com.footystars.foot8.buisness.model.dto.StandingData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandingDataWrapper {
    private String leagueName;
    private Long leagueId;
    private String leagueLogo;
    private Integer season;
    private List<StandingData> standingData;

}
