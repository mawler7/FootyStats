package com.footystars.model.dto.league;

import com.footystars.model.api.Standings;

import com.footystars.model.dto.fixture.LeagueMatchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LeagueDetailsDto {
    private Long id;
    private String leagueName;
    private String logo;
    private String flag;
    private Integer season;

    private List<Standings.StandingApi.StandingLeague.Standing> standings;

    private List<LeagueMatchDto> fixtures;

    public LeagueDetailsDto(Long id, String leagueName, String logo, String flag, Integer season) {
        this.id = id;
        this.leagueName = leagueName;
        this.logo = logo;
        this.flag = flag;
        this.season = season;
    }

}
