package com.footystars.model.dto.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.dto.team.TeamDetailsDto;
import com.footystars.model.dto.player.PlayerDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchInfo implements Serializable {
    private MatchDetailsDto matchDetails;
    private List<PlayerDto> players;
    private List<TeamDetailsDto> teams;
}
