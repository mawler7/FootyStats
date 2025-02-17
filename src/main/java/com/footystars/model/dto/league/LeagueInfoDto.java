package com.footystars.model.dto.league;

import com.footystars.model.dto.player.PlayerTeamSquadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LeagueInfoDto {
    private Long leagueId;
    private String name;
    private String flag;
    private String logo;
    private String type;
    private Integer year;
    private List<PlayerTeamSquadDto> players;

    public LeagueInfoDto(Long leagueId, String name, String flag, String logo, String type, Integer year) {
        this.leagueId = leagueId;
        this.name = name;
        this.flag = flag;
        this.logo = logo;
        this.type = type;
        this.year = year;
    }


}
