package com.footystars.foot8.persistence.entities.bookmakers.odd;

import com.footystars.foot8.persistence.entities.bookmakers.bet.Bet;
import com.footystars.foot8.persistence.entities.fixtures.fixture.Fixture;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "odds")
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;
    private double odd;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bet_id")
    private Bet bet;

    private String date;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Fixture fixture;

}
