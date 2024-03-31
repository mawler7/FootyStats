package com.footystars.foot8.persistence.entity.teams.team;

import com.footystars.foot8.persistence.entity.club.Club;
import com.footystars.foot8.persistence.entity.coachs.Coach;
import com.footystars.foot8.persistence.entity.competitions.Competition;
import com.footystars.foot8.persistence.entity.players.player.Player;
import com.footystars.foot8.persistence.entity.teams.statistics.TeamStats;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "club_id")
    private Club club;


    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToMany
    @JoinTable(name = "teams_players",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<Player> players;

    @ManyToMany
    @JoinTable(name = "teams_competitions",
    joinColumns = @JoinColumn(name = "team_id"),
    inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<Competition> competitions ;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TeamStats> statistics = new HashSet<>();


}
