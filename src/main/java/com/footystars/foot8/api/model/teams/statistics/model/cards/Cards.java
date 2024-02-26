package com.footystars.foot8.api.model.teams.statistics.model.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Cards implements Serializable {

    @Embedded
    @JsonProperty("red")
    private Card red;

    @Embedded
    @JsonProperty("yellow")
    private Card yellow;

}
