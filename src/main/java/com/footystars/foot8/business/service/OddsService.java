package com.footystars.foot8.business.service;


import com.footystars.foot8.api.model.odds.odd.Odd;
import com.footystars.foot8.business.model.dto.BetDto;
import com.footystars.foot8.mapper.BetMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OddsService {

    private final FixtureService fixtureService;
    private final BetService betService;
    private final BetMapper betMapper;
    private static final Logger logger = LoggerFactory.getLogger(OddsService.class);

    public void fetchOdds(@NotNull Odd odd) {
        var fixtureId = odd.getFixture().getFixtureId();

            var optionalFixture = fixtureService.findById(fixtureId);

            optionalFixture.ifPresent(fixture -> {
                var fixtureBets = fixture.getBets();

                var bookmakersDto = odd.getBookmakers().stream()
                        .filter(b -> b.getId().equals(1L) || b.getId().equals(2L) || b.getId().equals(8L));

                bookmakersDto.forEach(bookmakerDto -> {
                    var name = bookmakerDto.getName();
                    var bets = bookmakerDto.getBets();

                    bets.forEach(b -> {
                        var betDto = BetDto.builder()
                                .bookmaker(name)
                                .name(b.getName())
                                .stakes(b.getStakes())
                                .build();

                        var betEntity = betMapper.toEntity(betDto);
                        var savedBet = betService.save(betEntity);
                        fixtureBets.add(savedBet);
                    });
                });
                fixtureService.save(fixture);
                logger.info("Successfully saved fixture bet {}", fixtureId);
            });

        }
}