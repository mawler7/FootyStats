package com.footystars.foot8.persistence.entities.odds.bets;

import com.footystars.foot8.persistence.entities.ValueEntity;
import com.footystars.foot8.persistence.entities.fixtures.Fixture;
import com.footystars.foot8.persistence.entities.odds.bookmakers.BookmakerEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "bet")
public class BetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bookmaker_Id")
    private BookmakerEntity bookmaker;

    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValueEntity> values;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "match_id")
    private Fixture fixture;
}
