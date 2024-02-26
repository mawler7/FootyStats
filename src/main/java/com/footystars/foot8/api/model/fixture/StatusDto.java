package com.footystars.foot8.api.model.fixture;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class StatusDto implements Serializable {
    @JsonProperty("long")
    private String longName;
    @JsonProperty("short")
    private String shortName;
    private String elapsed;
}