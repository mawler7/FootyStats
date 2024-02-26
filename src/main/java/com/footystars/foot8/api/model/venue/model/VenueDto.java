package com.footystars.foot8.api.model.venue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class VenueDto implements Serializable {

    @JsonProperty("id")
    private Long venueId;
    @JsonProperty("name")
    private String venueName;
    private String city;
    private String address;
    @Column(insertable = false, updatable = false)
    private String country;
    private Long capacity;
    private String surface;
    private String image;
}