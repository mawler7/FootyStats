package com.footystars.foot8.api.model.players.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.players.info.PlayerInfo;
import com.footystars.foot8.api.model.players.statistics.PlayerStatistic;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class PlayerStatistics implements Serializable {

    @JsonProperty("player")
    private PlayerInfo playerInfo;

    private List<PlayerStatistic> statistics;
}
