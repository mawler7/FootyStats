package com.footystars.foot8.api.model.fixture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embedded;
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
public class FixtureInfo implements Serializable {
    @JsonProperty("id")
    private Long id;
    private String referee;
    private String timezone;
    private String date;
    private Long timestamp;
    @Embedded
    private PeriodsDto periods;
    @Embedded
    private StatusDto status;
    @Embedded
    private VenueDto venue;

}