package com.footystars.foot8.api.model.fixtures.fixture.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.fixtures.fixture.info.periods.Periods;
import com.footystars.foot8.api.model.fixtures.fixture.info.status.Status;
import com.footystars.foot8.persistence.entities.venues.VenueDto;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
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
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureInfo implements Serializable {

    @Transient
    @JsonProperty("id")
    private Long fixtureId;
    private String referee;
    private String timezone;
    private String date;
    private String timestamp;

    private Status status;

    private VenueDto venue;

    private Periods periods;


}
