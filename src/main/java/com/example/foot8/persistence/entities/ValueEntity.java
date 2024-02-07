package com.example.foot8.persistence.entities;

import com.example.foot8.persistence.entities.odds.bets.BetEntity;
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
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "value")
public class ValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private double odd;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bet_id")
    private BetEntity bet;
//
//    @ManyToOne
//    @JoinColumn(name = "value_fixture_id")
//    private MatchEntity match;

    private String date;

    private Long fixtureId;

}
