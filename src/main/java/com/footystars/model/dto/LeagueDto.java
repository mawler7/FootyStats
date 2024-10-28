package com.footystars.model.dto;

import com.footystars.model.api.Standings;
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
    private String type;
    private Integer season;
    private Boolean current;

    private List<Standings.StandingApi.StandingLeague.Standing> standings;

    private List<FixtureDto> fixtures;


}
