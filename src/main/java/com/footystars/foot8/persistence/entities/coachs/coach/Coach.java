package com.footystars.foot8.persistence.entities.coachs.coach;

import com.footystars.foot8.api.model.coaches.carrer.Careeer;
import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.persistence.entities.teams.team.Team;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coaches")
public class Coach {

    @Id
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
    @CollectionTable(name = "coaches_careers", joinColumns = @JoinColumn(name = "coachs_id"))
    private List<Careeer> career;

    @OneToOne
    private Team team;

}
