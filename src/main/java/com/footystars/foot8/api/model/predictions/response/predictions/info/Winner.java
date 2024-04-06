package com.footystars.foot8.api.model.predictions.response.predictions.info;

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
public class Winner implements Serializable {

    @JsonProperty("id")
    private Long winnerId;

    @JsonProperty("name")
    private String winnerName;

    private String comment;

}
