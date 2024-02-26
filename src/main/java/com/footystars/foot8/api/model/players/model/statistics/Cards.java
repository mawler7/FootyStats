package com.footystars.foot8.api.model.players.model.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class Cards implements Serializable {

    private Integer yellow;

    @JsonProperty("yellowred")
    private Integer yellowRed;

    private Integer red;
}
