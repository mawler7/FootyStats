package com.footystars.foot8.api.model.players.statistics.shots;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shots implements Serializable {

    @JsonProperty("on")
    private Integer shotsOnTarget;
    @JsonProperty("total")
    private Integer shotsTotal;

}
