package com.footystars.persistence.repository;

import com.footystars.model.dto.bet.BetDto;
import com.footystars.model.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {

    @Query("SELECT new com.footystars.model.dto.bet.BetDto(" +
            "b.fixture.id, b.bookmakerName, b.betName, b.value, AVG(b.odd)) " +
            "FROM Bet b " +
            "WHERE b.fixture.id IN :fixtureIds " +
            "   AND b.betName in ('Match Winner', 'Double Chance', 'Goals Over/Under', 'Total - Home', 'Total - Away') " +
            "GROUP BY b.fixture.id, b.bookmakerName, b.betName, b.value")
    List<BetDto> findAverageOddsByFixtures(@Param("fixtureIds") List<Long> fixtureIds);


    @Query("SELECT new com.footystars.model.dto.bet.BetDto(" +
            "b.bookmakerName, b.betName, b.value, AVG(b.odd)) " +
            "FROM Bet b " +
            "WHERE b.fixture.id = :fixtureId " +
            "GROUP BY b.bookmakerName, b.betName, b.value")
    List<BetDto> findAverageOddsByFixtureId(@Param("fixtureId") Long fixtureId);




}
