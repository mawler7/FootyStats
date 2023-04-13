package com.example.foot8.buisness.match.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixtureDto {

    private Long id;
    private String referee;
    private String timezone;
    private String date;
    private long timestamp;

    private Map<String, Long> periods;

    private VenueMatchDto venue;

    private StatusDto status;

}
