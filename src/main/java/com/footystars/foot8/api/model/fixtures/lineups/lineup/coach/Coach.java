package com.footystars.foot8.api.model.fixtures.lineups.lineup.coach;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Coach implements Serializable {
    @JsonProperty("id")
    private Long coachId;
    @JsonProperty("name")
    private String coachName;
    private String photo;

}
