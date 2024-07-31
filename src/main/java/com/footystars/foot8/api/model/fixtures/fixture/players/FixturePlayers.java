package com.footystars.foot8.api.model.fixtures.fixture.players;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.business.model.dto.PlayerDto;
import com.footystars.foot8.business.model.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixturePlayers implements Serializable {

    private TeamDto team;
    private List<PlayerDto> players;

}
