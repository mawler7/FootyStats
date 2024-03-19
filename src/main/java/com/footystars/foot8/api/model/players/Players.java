package com.footystars.foot8.api.model.players;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.players.paging.Paging;
import com.footystars.foot8.api.model.players.player.PlayerStatistics;
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
public class Players implements Serializable {

    @JsonProperty("response")
    private List<PlayerStatistics> playerList;
    private Paging paging;

}
