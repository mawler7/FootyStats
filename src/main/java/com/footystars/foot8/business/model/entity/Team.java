package com.footystars.foot8.business.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teams", uniqueConstraints = {@UniqueConstraint(columnNames = {"club_id", "season_id", "statistics_id"})})
public class Team implements Serializable {

    public static final String JOINTABLE_PLAYERS_NAME = "players_teams";
    public static final String JOINCOLUMNS_JOINCOLUMN_PLAYERS_NAME = "player_id";
    public static final String INVERSEJOINCOLUMNS_JOINCOLUMN_PLAYERS_NAME = "team_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clubId;
    
    private String name;
    private String code;
    private String logo;
    private Integer founded;
    private boolean national;

    private String country;

    private String venue;
    private String address;
    private String city;

    private Long capacity;
    private String surface;
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "teams")
    private List<Player> players = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "statistics_id")
    private TeamStats statistics;

}
