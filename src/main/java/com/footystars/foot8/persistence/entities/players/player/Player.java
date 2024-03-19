package com.footystars.foot8.persistence.entities.players.player;

import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.persistence.entities.players.seaon.PlayerSeason;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "players")
public class Player {

    @Id
    private Long id;

    private String name;
    private String firstname;
    private String lastname;
    private String nationality;
    private Integer age;
    private String height;
    private String weight;
    private String photo;
    private boolean injured;

    @Embedded
    private Birth birth;

}