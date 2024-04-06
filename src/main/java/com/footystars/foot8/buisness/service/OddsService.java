package com.footystars.foot8.buisness.service;


import com.footystars.foot8.api.model.odds.odd.Odd;
import com.footystars.foot8.buisness.model.dto.BetDto;
import com.footystars.foot8.mapper.BetMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OddsService {

    private final FixtureService fixtureService;
    private final BetService betService;
    private final BetMapper betMapper;

    public void fetchOdds(@NotNull Odd odd) {
        var fixtureId = odd.getFixture().getFixtureId();

        if (fixtureId != null) {
            var optionalFixture = fixtureService.findById(fixtureId);

            optionalFixture.ifPresent(fixture -> {
                var fixtureBets = fixture.getBets();
                var bookmakersDto = odd.getBookmakers();

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
            });
        }
    }
}