package com.footystars.foot8.api.model.teams.info.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.info.model.TeamDto;
import com.footystars.foot8.api.model.venue.model.VenueDto;
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
public class TeamInfoDto {
    @JsonProperty("team")
    private TeamDto teamDto;
    private VenueDto venue;

}
