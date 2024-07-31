package com.footystars.foot8.api.model.predictions.response.h2h;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.fixture.info.status.Status;
import com.footystars.foot8.business.model.dto.VenueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureH2H implements Serializable {

    private Long id;
    private ZonedDateTime date;
    private String referee;
    private VenueDto venue;
    private Status status;

}
