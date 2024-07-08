package com.footystars.foot8.api.model.players.statistics.penalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Penalty implements Serializable {

    @JsonProperty("commited")
    private Integer penaltiesCommitted;
    @JsonProperty("scored")
    private Integer penaltiesScored;
    @JsonProperty("missed")
    private Integer penaltiesMissed;
    @JsonProperty("saved")
    private Integer penaltiesSaved;
    @JsonProperty("won")
    private Integer penaltiesWon;
}
