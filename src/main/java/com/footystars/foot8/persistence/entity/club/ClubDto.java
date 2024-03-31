package com.footystars.foot8.persistence.entity.club;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.persistence.entity.countries.CountryDto;
import com.footystars.foot8.persistence.entity.venues.VenueDto;
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
public class ClubDto implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String logo;
    private Integer founded;
    private boolean national;
    private CountryDto country;
    private VenueDto venue;
}