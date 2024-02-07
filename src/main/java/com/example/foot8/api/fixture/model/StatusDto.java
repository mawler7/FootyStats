package com.example.foot8.api.fixture.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusDto {

    @JsonProperty("long")
    private String longName;
    @JsonProperty("short")
    private String shortName;
    private String elapsed;
}