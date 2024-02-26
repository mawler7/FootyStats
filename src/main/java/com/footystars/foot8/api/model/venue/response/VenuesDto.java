package com.footystars.foot8.api.model.venue.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.venue.model.VenueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VenuesDto {
    //    private String get;
//    private Parameters parameters;
//    private List<String> errors;
//    private Integer results;
//    private Paging paging;
    @JsonProperty("response")
    private List<VenueDto> venues;
}
