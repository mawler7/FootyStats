package com.footystars.foot8.api.model.teams;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Teams implements Serializable {

    @JsonProperty("response")
    private List<TeamApi> teamsApi;

}
