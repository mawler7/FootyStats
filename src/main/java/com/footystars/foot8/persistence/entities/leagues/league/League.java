package com.footystars.foot8.persistence.entities.leagues.league;


import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeason;
import jakarta.persistence.CascadeType;
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

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "leagues")
public class League {

    @Id
    private Long id;

    private String name;
    private String type;
    private String logo;
    private String countryName;
    private String countryCode;
    private String countryFlag;

    @OneToMany(mappedBy = "league", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<LeagueSeason> seasons;

}