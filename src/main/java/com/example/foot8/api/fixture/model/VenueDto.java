package com.example.foot8.api.fixture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VenueDto {

    private Long id;
    private String name;
    private String city;
}
