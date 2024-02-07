package com.example.foot8.persistence.entities.teams;

import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import com.example.foot8.persistence.entities.venues.VenueEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.io.Serializable;


@Data
@Builder
@Getter
@Setter
public class TeamEntityDto  implements Serializable {
    private Long id;
    private String name;
    private String logo;

    @ManyToOne
    private LeagueEntity league;
    private VenueEntity venue;

}