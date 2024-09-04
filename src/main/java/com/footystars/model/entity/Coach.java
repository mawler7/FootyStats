package com.footystars.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.footystars.model.api.Coaches;
import com.footystars.model.api.Trophies;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Coach implements Serializable {

    @Id
    private Long id;

    private String name;
    private String firstname;
    private String lastname;

    @Embedded
    private Coaches.CoachDto.Birth birth;

    @Embedded
    private Coaches.CoachDto.Team team;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "coaches_careers", joinColumns = @JoinColumn(name = "coach_id"))
    private List<Coaches.CoachDto.Career> career;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "coaches_trophies", joinColumns = @JoinColumn(name = "coach_id"))
    private List<Trophies.Trophy> trophies = new ArrayList<>();

}
