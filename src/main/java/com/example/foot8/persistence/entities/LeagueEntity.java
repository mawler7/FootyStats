package com.example.foot8.persistence.entities;

import com.example.foot8.buisness.match.model.LeagueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Long season;
    private String round;

    public LeagueEntity(LeagueDto leagueDto) {
        this.id = leagueDto.getId();
        this.name = leagueDto.getName();
        this.country = leagueDto.getCountry();
        this.logo = leagueDto.getLogo();
        this.flag = leagueDto.getFlag();
        this.season = leagueDto.getSeason();
        this.round = leagueDto.getRound();
    }

}