package com.footystars.foot8.persistence.entities.coachs.coach;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.coaches.carrer.Careeer;
import com.footystars.foot8.api.model.coaches.team.Team;
import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.persistence.entities.teams.team.TeamDto;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
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
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoachDto implements Serializable {
    private Long id;

    private String name;
    private String firstName;
    private String lastName;

    @Embedded
    private Birth birth;
    private int age;
    private String nationality;
    private String height;

    @ElementCollection
    @CollectionTable(name="coachs_careers", joinColumns = @JoinColumn(name = "coachs_id"))
    private List<Careeer> careers;

    private TeamDto teamDto;

}