package com.footystars.model.dto.league;

import com.footystars.model.api.Standings;
import com.footystars.model.dto.fixture.MatchDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class LeagueDto implements Serializable {

    private Long id;
    private String leagueName;
    private String logo;
    private String flag;
    private Integer season;
    private String type;
    private Boolean current;

    private List<Standings.StandingApi.StandingLeague.Standing> standings;

    private List<MatchDto> fixtures;


}
