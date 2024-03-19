package com.footystars.foot8.persistence.entities.coachs.season;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.persistence.entities.coachs.coach.CoachDto;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeasonDto;
import jakarta.persistence.Embeddable;
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
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoachSeasonDto implements Serializable {
    private Long id;
    private String startDate;
    private String endDate;
    private CoachDto coach;
    private TeamSeasonDto teamSeason;
}