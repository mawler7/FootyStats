package com.footystars.model.dto;


import com.footystars.model.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponseDto {
    private Player.PlayerInfo info;
    private List<PlayerLastMatchDto> lastMatches;
    private List<PlayerCareerDto> carrer;
}
