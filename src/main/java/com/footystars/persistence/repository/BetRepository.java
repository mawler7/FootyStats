package com.footystars.persistence.repository;

import com.footystars.model.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    @Query("SELECT b.fixture.id, b.value, AVG(b.odd) FROM Bet b WHERE b.fixture.id IN :fixtureIds AND b.value IN ('Home', 'Draw', 'Away') GROUP BY b.fixture.id, b.value")
    List<Object[]> findAverageOddsByFixtures(@Param("fixtureIds") List<Long> fixtureIds);
}
