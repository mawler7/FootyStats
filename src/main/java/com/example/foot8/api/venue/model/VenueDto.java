package com.example.foot8.api.venue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VenueDto  {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String country;
    private Long capacity;
    private String surface;
    private String image;
}