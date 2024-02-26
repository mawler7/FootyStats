package com.footystars.foot8.api.model.players.response;

import com.footystars.foot8.api.model.players.model.PlayerResponseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlayerResponse {
    private List<PlayerResponseData> response;
}
