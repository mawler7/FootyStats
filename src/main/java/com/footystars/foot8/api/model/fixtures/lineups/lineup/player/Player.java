package com.footystars.foot8.api.model.fixtures.lineups.lineup.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Player implements Serializable {
    @JsonProperty("id")
    private Long playerId;
    @JsonProperty("name")
    private String playerName;
    private Integer number;
    private String pos;
    private String grid;

}
