package com.footystars.foot8.api.model.players.model;

import com.footystars.foot8.api.model.players.model.player.Player;
import com.footystars.foot8.api.model.players.model.statistics.PlayerStatistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlayerResponseData {

    @NotNull
    private Player player;

    @NotNull
    private List<PlayerStatistics> statistics;
}
