package com.footystars.foot8.persistence.entities.teams.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.info.TeamInfo;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeasonDto;

import com.footystars.foot8.persistence.entities.venues.VenueDto;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto implements Serializable {
   private Long id;

   @JsonProperty("team")
   private TeamInfo teamInfo;
   private VenueDto venue;
}