package com.footystars.model.dto.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.api.Players;
import com.footystars.model.dto.fixture.FixturePlayerDto;
import com.footystars.model.entity.Player;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Player}
 */
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDto implements Serializable {
   private Player.PlayerInfo info;
   private Players.PlayerStats statistics;
   private List<FixturePlayerDto> fixtures;
}