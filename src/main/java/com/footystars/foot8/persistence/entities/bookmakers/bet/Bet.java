package com.footystars.foot8.persistence.entities.bookmakers.bet;

import com.footystars.foot8.persistence.entities.bookmakers.odd.Odds;
import com.footystars.foot8.persistence.entities.bookmakers.bookmaker.Bookmaker;
import com.footystars.foot8.persistence.entities.fixtures.fixture.Fixture;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bookmaker_id")
    private Bookmaker bookmaker;

    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Odds> odds;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;
}
