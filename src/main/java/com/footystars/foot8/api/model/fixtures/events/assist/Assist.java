package com.footystars.foot8.api.model.fixtures.events.assist;

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
public class Assist implements Serializable {

    @JsonProperty("id")
    private Long assistPlayerId;

    @JsonProperty("name")
    private String assistPlayerName;

}
