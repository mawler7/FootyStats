package com.footystars.foot8.business.model.entity;

import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.api.model.players.statistics.PlayerStats;
import com.footystars.foot8.api.model.sidelined.SidelinedApi;
import com.footystars.foot8.api.model.trophies.Trophy;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "players")
public class Player implements Serializable {

    @Id
    @Column(unique = true)
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
    private String zodiac;
    private Birth birth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name ="player_id"),
            inverseJoinColumns = @JoinColumn(name ="team_id" ))
    private List<Team> teams = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(unique = true)
@ElementCollection
@CollectionTable(name = "player_statistics", joinColumns = @JoinColumn(name = "player_id"))
    private List<PlayerStats> statistics = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "players_sidelined", joinColumns = @JoinColumn(name = "player_id"))
    private List<SidelinedApi> sidelined;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "players_trophies", joinColumns = @JoinColumn(name = "player_id"))
    private List<Trophy> trophies = new ArrayList<>();


}