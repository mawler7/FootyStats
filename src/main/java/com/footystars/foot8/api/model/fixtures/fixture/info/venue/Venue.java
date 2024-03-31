package com.footystars.foot8.api.model.fixtures.fixture.info.venue;

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
public class Venue implements Serializable {

    @JsonProperty("id")
    private Long venueId;

    @JsonProperty("name")
    private String venueName;

    @JsonProperty("city")
    private String venueCity;
}