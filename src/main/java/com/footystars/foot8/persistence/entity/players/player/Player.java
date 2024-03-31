package com.footystars.foot8.persistence.entity.players.player;

import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.persistence.entity.competitions.Competition;
import com.footystars.foot8.persistence.entity.players.statistics.PlayerStats;
import com.footystars.foot8.persistence.entity.teams.team.Team;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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

    private String zodiac;

    private Birth birth;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayerStats> statistics = new ArrayList<>();

    @ManyToMany(mappedBy = "players")
    private Set<Team> teams;

    @ManyToMany(mappedBy = "players")
    private Set<Competition> competitions;

}