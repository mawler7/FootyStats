package com.example.foot8.api.players.model;

import com.example.foot8.api.players.model.player.Player;
import com.example.foot8.api.players.model.statistics.PlayerStatistics;
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
