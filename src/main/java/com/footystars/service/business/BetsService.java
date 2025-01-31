package com.footystars.service.business;

import com.footystars.model.api.Odds;
import com.footystars.model.entity.Bet;
import com.footystars.model.entity.Fixture;
import com.footystars.persistence.repository.BetRepository;
import com.footystars.persistence.repository.FixtureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing betting odds for fixtures.
 */
@Service
@RequiredArgsConstructor
public class BetsService {

    private final FixtureRepository fixtureRepository;
    private final BetRepository betRepository;

    /**
     * Updates betting odds for a given fixture by processing odds data from external sources.
     *
     * @param oddsResponse The odds response containing betting data.
     * @param fixtureId    The ID of the fixture to update odds for.
     */
    @Transactional
    public void updateOddsForFixture(@NotNull Odds.OddsResponse oddsResponse, @NotNull Long fixtureId) {
        fixtureRepository.findById(fixtureId).ifPresent(fixture -> {
            List<Bet> betsToSave = new ArrayList<>();

            oddsResponse.getBookmakers().forEach(bookmaker ->
                    bookmaker.getBets().forEach(bet ->
                            bet.getValues().forEach(value -> {
                                Bet betEntity = createBetEntity(bookmaker.getName(), bet.getName(), value.getValue(), value.getOdd(), fixture);
                                betsToSave.add(betEntity);
                            })
                    )
            );

            if (!betsToSave.isEmpty()) {
                betRepository.saveAll(betsToSave);
            }
        });
    }

    /**
     * Creates a new Bet entity based on provided data.
     *
     * @param bookmakerName The name of the bookmaker.
     * @param betName       The name of the bet type.
     * @param value         The specific betting value.
     * @param odd           The odd associated with the bet.
     * @param fixture       The fixture associated with the bet.
     * @return A Bet entity.
     */
    private Bet createBetEntity(String bookmakerName, String betName, String value, Double odd, Fixture fixture) {
        return Bet.builder()
                .bookmakerName(bookmakerName)
                .betName(betName)
                .value(value)
                .odd(odd)
                .fixture(fixture)
                .lastUpdated(ZonedDateTime.now())
                .build();
    }
}
