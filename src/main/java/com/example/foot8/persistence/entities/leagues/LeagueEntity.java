package com.example.foot8.persistence.entities.leagues;

import com.example.foot8.persistence.entities.countries.CountryEntity;
import com.example.foot8.persistence.entities.seasons.SeasonsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Table(name = "leagues")
public class LeagueEntity {

    @Id
    private Long id;

    private String name;
    private String type;
    private String logo;

    @ManyToOne
    @JoinColumn(name = "country_code")
    private CountryEntity country;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<SeasonsEntity> seasons;

}