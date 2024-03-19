package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.odds.BetApi;
import com.footystars.foot8.api.model.odds.BookmakerApi;
import com.footystars.foot8.api.model.odds.OddValue;
import com.footystars.foot8.persistence.entities.bookmakers.odd.Odds;
import com.footystars.foot8.persistence.entities.bookmakers.bet.Bet;
import com.footystars.foot8.persistence.entities.bookmakers.bookmaker.Bookmaker;
import com.footystars.foot8.persistence.repository.BookmakerRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Long.parseLong;


@Service
@RequiredArgsConstructor
public class BookmakerService {

    private final BookmakerRepository bookmakerRepository;
    private final BetService betService;

    public void saveBookmakers(@NotNull List<BookmakerApi> bookmakerDtos, String fixtureId) {
        List<Bookmaker> bookmakerEntities = bookmakerDtos.stream()
                .map(bookmakerDto -> mapToEntity(bookmakerDto, fixtureId))
                .toList();
        bookmakerRepository.saveAll(bookmakerEntities);
    }

    private Bookmaker mapToEntity(@NotNull BookmakerApi bookmakerDto, String fixtureId) {
        return bookmakerRepository.findById(bookmakerDto.getId())
                .orElseGet(() -> {
                    Bookmaker bookmakerEntity = new Bookmaker();
                    bookmakerEntity.setName(bookmakerDto.getName());

                    if (bookmakerDto.getBets() != null) {
                        List<Bet> bets = bookmakerDto.getBets().stream()
                                .map(betDto -> mapToEntity(betDto, bookmakerEntity, fixtureId))
                                .toList();
                        bookmakerEntity.setBets(bets);
                    }

                    return bookmakerEntity;
                });
    }

    private Bet mapToEntity(@NotNull BetApi betDto, Bookmaker bookmakerEntity, String fixtureId) {
        return betService.findById(betDto.getId())
                .orElseGet(() -> {
                    Bet betEntity = new Bet();
                    betEntity.setName(betDto.getName());
                    betEntity.setBookmaker(bookmakerEntity);

                    if (betDto.getValues() != null) {
                        List<Odds> valueEntities = betDto.getValues().stream()
                                .map(oddValueDto -> mapToEntity(oddValueDto, betEntity, fixtureId))
                                .toList();
//                        betEntity.setValues(valueEntities);
                    }

                    return betEntity;
                });
    }

    private Odds mapToEntity(@NotNull OddValue oddValueDto, Bet betEntity, String fixtureId) {
        Odds valueEntity = new Odds();
        valueEntity.setValue(oddValueDto.getValue());
        valueEntity.setOdd(oddValueDto.getOdd());

//        valueEntity.setFixtureId(parseLong(fixtureId));

        valueEntity.setBet(betEntity);
        return valueEntity;
    }
}
