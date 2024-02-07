package com.example.foot8.persistence.entities.seasons;

import com.example.foot8.persistence.entities.fixtures.FixtureEntity;
import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "seasons")
public class SeasonsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;
    private String startDate;
    private String endDate;
    private Boolean current;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private LeagueEntity league;


    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private List<FixtureEntity> fixtures;


}