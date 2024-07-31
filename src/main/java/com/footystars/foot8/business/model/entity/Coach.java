package com.footystars.foot8.business.model.entity;

import com.footystars.foot8.api.model.coaches.carrer.Career;
import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.api.model.trophies.Trophy;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coaches")
public class Coach implements Serializable {

    @Id
    private Long id;
    private String name;
    private String firstname;
    private String lastname;

    private Birth birth;
    private int age;
    private String nationality;
    private String height;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "coaches_careers", joinColumns = @JoinColumn(name = "coach_id"))
    private List<Career> career;

    @OneToMany(mappedBy = "coach")
    private List<Team> teams = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "coaches_trophies", joinColumns = @JoinColumn(name = "coach_id"))
    private List<Trophy> trophies;


}
