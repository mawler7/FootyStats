package com.footystars.foot8.buisness.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class VenueDto implements Serializable {

    private Long id;
    private String name;
    private String address;
    private String city;
    private Long capacity;
    private String surface;
    private String image;
}