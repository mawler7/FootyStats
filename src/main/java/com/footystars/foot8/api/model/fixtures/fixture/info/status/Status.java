package com.footystars.foot8.api.model.fixtures.fixture.info.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status implements Serializable {

    @JsonProperty("long")
    private String fullStatus;

    @JsonProperty("short")
    private String status;

    private String elapsed;


}