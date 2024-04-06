package com.footystars.foot8.api.model.lineups.lineup.team.colors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.lineups.lineup.player.LineupPlayer;
import com.footystars.foot8.api.model.lineups.lineup.team.colors.goalkeeper.Goalkeeper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Colors implements Serializable {
    private LineupPlayer player;
    private Goalkeeper goalkeeper;
}
