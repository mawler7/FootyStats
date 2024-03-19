package com.footystars.foot8.persistence.entities.teams.team;

import com.footystars.foot8.api.model.teams.info.TeamInfo;
import com.footystars.foot8.persistence.entities.fixtures.fixture.Fixture;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import com.footystars.foot8.persistence.entities.venues.Venue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "teams")
public class  Team  {

    @Id
    private Long id;

    private String name;
    private String code;
    private String logo;
    private String country;
    private Integer founded;
    private boolean national;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany
    private Set<Team> teams;



}