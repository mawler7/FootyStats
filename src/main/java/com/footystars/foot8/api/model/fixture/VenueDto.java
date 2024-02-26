package com.footystars.foot8.api.model.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class VenueDto implements Serializable {
    @JsonProperty("id")
    private Long venueId;

    @JsonProperty("name")
    private String venueName;

    @JsonProperty("city")
    private String venueCity;
}