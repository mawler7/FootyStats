package com.footystars.foot8.persistence.entity.coachs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.coaches.carrer.Career;
import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.persistence.entity.teams.team.TeamDto;
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
public class CoachDto implements Serializable {
    private Long id;
    private String name;
    private String firstname;
    private String lastname;
    private Birth birth;
    private int age;
    private String nationality;
    private String height;
    private TeamDto team;
    private List<Career> career;
}