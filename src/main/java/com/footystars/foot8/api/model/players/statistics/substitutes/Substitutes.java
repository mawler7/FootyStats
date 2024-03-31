package com.footystars.foot8.api.model.players.statistics.substitutes;

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
public class Substitutes implements Serializable {

    @JsonProperty("in")
    private Integer substitutedIn;
    @JsonProperty("out")
    private Integer substitutedOut;
    private Integer bench;
}
