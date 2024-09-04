package com.footystars.service.business;

import com.footystars.model.api.Odds;
import com.footystars.model.entity.Bet;
import com.footystars.persistence.repository.BetRepository;
import com.footystars.persistence.repository.FixtureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BetsService {


    private final FixtureRepository fixtureRepository;
    private final BetRepository betRepository;

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
                                            .build();
                                    betRepository.save(betEntity);
                                }))));
    }

}






