package com.example.foot8.buisness.venue.response;

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
public class Response {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private Long capacity;
    private String surface;
    private String image;
}
