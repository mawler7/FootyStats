package com.footystars.foot8.api.model.players.statistics.tackles;

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
public class Tackles implements Serializable {
    @JsonProperty("total")
    private Integer tacklesTotal;
    private Integer blocks;
    private Integer interceptions;

}
