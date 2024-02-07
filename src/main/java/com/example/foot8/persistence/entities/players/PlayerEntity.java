package com.example.foot8.persistence.entities.players;

import com.example.foot8.api.players.model.player.Birth;
import com.example.foot8.persistence.entities.players.statistics.PlayerStatisticsEntity;
import com.example.foot8.persistence.entities.teams.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "player")
public class PlayerEntity {

    @Id
    private Long id;
    private String name;
    private String firstname;
    private String lastname;
    private Integer age;

    @Embedded
    private Birth birth;
    private String nationality;
    private String height;
    private String weight;
    private Boolean injured;
    private String photo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<PlayerStatisticsEntity> statistics;

}