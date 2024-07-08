package com.footystars.foot8.api.model.coaches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.business.model.dto.CoachDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coaches implements Serializable {

    private List<CoachDto> response;

}
