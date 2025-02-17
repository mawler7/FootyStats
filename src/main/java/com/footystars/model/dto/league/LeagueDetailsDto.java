package com.footystars.model.dto.league;

import com.footystars.model.api.Standings;

import com.footystars.model.dto.fixture.LeagueMatchDto;
import com.footystars.model.dto.fixture.MatchDto;
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
    private LeagueInfoDto leagueInfo;
    private List<Standings.StandingApi.StandingLeague.Standing> standings;
    private List<MatchDto> fixtures;

}
