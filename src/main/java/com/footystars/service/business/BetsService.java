package com.footystars.service.business;

import com.footystars.model.api.Odds;
import com.footystars.model.entity.Bet;
import com.footystars.persistence.repository.BetRepository;
import com.footystars.persistence.repository.FixtureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class BetsService {


    private final FixtureRepository fixtureRepository;
    private final BetRepository betRepository;


    private static final Set<String> VALID_BET_TYPES = Set.of(
            "Anytime Goal Scorer",
            "Both Teams Score",
            "Both Teams To Score in Both Halves",
            "Cards Over/Under",
            "Corners Over Under",
            "Double Chance",
            "Exact Score",
            "Goals Over/Under",
            "Home/Away",
            "Match Winner",
            "Player to be booked",
            "Results/Both Teams Score",
            "Total - Away",
            "Total Goals/Both Teams To Score",
            "Total - Home",
            "Yellow Over/Under (1st Half)"
    );

    @Transactional
    public void updateOddsForFixture(@NotNull Odds.OddsResponse oddsResponse, @NotNull Long fixtureId) {
        fixtureRepository.findById(fixtureId).ifPresent(fixture -> oddsResponse.getBookmakers()
                .forEach(bookmaker -> bookmaker.getBets()
                        .forEach(bet -> bet.getValues()
                                .forEach(value -> {
                                    var betEntity = Bet.builder()
                                            .bookmakerName(bookmaker.getName())
                                            .betName(bet.getName())
                                            .value(value.getValue())
                                            .odd(value.getOdd())
                                            .fixture(fixture)
                                            .lastUpdated(ZonedDateTime.now())
                                            .build();
                                    betRepository.save(betEntity);
                                }))));
    }


}






