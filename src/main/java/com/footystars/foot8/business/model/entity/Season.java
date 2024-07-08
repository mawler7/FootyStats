package com.footystars.foot8.business.model.entity;

import com.footystars.foot8.api.model.leagues.league.coverage.Coverage;
import com.footystars.foot8.api.model.standings.standing.Standing;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seasons", uniqueConstraints = {@UniqueConstraint(columnNames = {"year", "league_id"})})
public class Season implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean current;
    private int year;
    private String startDate;
    private String endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "league_id")
    private League league;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Fixture> fixtures = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "seasons_standings", joinColumns = @JoinColumn(name = "season_id"))
    private List<Standing> standings = new ArrayList<>();



    @Embedded
    private Coverage coverage;

    @OneToMany(mappedBy = "season")
    private List<Team> teams = new ArrayList<>();

}