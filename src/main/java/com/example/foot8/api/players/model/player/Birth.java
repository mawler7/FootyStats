package com.example.foot8.api.players.model.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Birth implements Serializable {

    @JsonProperty("date")
    private String birthDate;

    @JsonProperty("place")
    private String birthPlace;

    @JsonProperty("country")
    private String birthCountry;

}
