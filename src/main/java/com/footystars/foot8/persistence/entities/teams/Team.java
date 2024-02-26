package com.footystars.foot8.persistence.entities.teams;

import com.footystars.foot8.api.model.venue.model.VenueDto;
import com.footystars.foot8.persistence.entities.leagues.League;
import com.footystars.foot8.persistence.entities.players.Player;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teams")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long teamId;
    private String name;
    private String code;
    private String country;
    private Integer founded;
    private Boolean national;
    private String logo;
    private Integer season;

    @Embedded
    private VenueDto venue;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private  League league;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Player> players;


}