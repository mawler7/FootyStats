package com.footystars.model.dto.player;

import com.footystars.model.api.Players;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerInfoDto {
    private Long playerId;
    private String name;
    private String photo;
    private List<Players.PlayerStats> statistics;
    private List<PlayerLastMatchDto> lastMatches;
}
