package com.example.foot8.persistence.entities;

import com.example.foot8.buisness.match.model.LeagueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "league")
public class LeagueEntity {

    @Id
    private Long id;

    private String name;
    private String country;
    private String logo;
    private String flag;

    @OneToMany(mappedBy = "league")
    private Set<TeamEntity> teams;

    public LeagueEntity(LeagueDto leagueDto) {
        this.id = leagueDto.getId();
        this.name = leagueDto.getName();
        this.country = leagueDto.getCountry();
        this.logo = leagueDto.getLogo();
        this.flag = leagueDto.getFlag();
    }

}