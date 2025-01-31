package com.footystars.persistence.repository;

import com.footystars.model.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {

    @Query("SELECT b.fixture.id, b.value, AVG(b.odd) FROM Bet b WHERE b.fixture.id IN :fixtureIds AND b.value IN ('Home', 'Draw', 'Away') GROUP BY b.fixture.id, b.value")
    List<Object[]> findAverageOddsByFixtures(@Param("fixtureIds") List<Long> fixtureIds);


    @Query("SELECT b.betName, AVG(b.odd) AS averageOdd, b.value, b.bookmakerName " +
            "FROM Bet b " +
            "WHERE b.fixture.id = :fixtureId " +
            "GROUP BY b.betName, b.value, b.bookmakerName")
    List<Object[]> findAverageOddsByFixtureId(@Param("fixtureId") Long fixtureId);


}
