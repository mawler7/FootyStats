package com.example.foot8.api.teams.info.response;

import com.example.foot8.api.teams.info.model.TeamInfo;
import com.example.foot8.api.venue.model.VenueDto;
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
public class TeamInfoResponseData {
    private TeamInfo team;
    private VenueDto venue;

}
