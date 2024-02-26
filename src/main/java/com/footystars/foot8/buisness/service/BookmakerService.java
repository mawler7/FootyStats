package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.odds.model.BetDto;
import com.footystars.foot8.api.model.odds.model.BookmakerDto;
import com.footystars.foot8.api.model.odds.model.OddValueDto;
import com.footystars.foot8.persistence.entities.ValueEntity;
import com.footystars.foot8.persistence.entities.odds.bets.BetEntity;
import com.footystars.foot8.persistence.entities.odds.bookmakers.BookmakerEntity;
import com.footystars.foot8.persistence.repository.BookmakerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Long.parseLong;


@Service
@RequiredArgsConstructor
public class BookmakerService {

    private final BookmakerRepository bookmakerRepository;
    private final BetService betService;

    public void saveBookmakers(@NotNull List<BookmakerDto> bookmakerDtos, String fixtureId) {
        List<BookmakerEntity> bookmakerEntities = bookmakerDtos.stream()
                .map(bookmakerDto -> mapToEntity(bookmakerDto, fixtureId))
                .toList();
        bookmakerRepository.saveAll(bookmakerEntities);
    }

    private BookmakerEntity mapToEntity(@NotNull BookmakerDto bookmakerDto, String fixtureId) {
        return bookmakerRepository.findById(bookmakerDto.getId())
                .orElseGet(() -> {
                    BookmakerEntity bookmakerEntity = new BookmakerEntity();
                    bookmakerEntity.setName(bookmakerDto.getName());

                    if (bookmakerDto.getBets() != null) {
                        List<BetEntity> bets = bookmakerDto.getBets().stream()
                                .map(betDto -> mapToEntity(betDto, bookmakerEntity, fixtureId))
                                .toList();
                        bookmakerEntity.setBets(bets);
                    }

                    return bookmakerEntity;
                });
    }

    private BetEntity mapToEntity(@NotNull BetDto betDto, BookmakerEntity bookmakerEntity, String fixtureId) {
        return betService.findById(betDto.getId())
                .orElseGet(() -> {
                    BetEntity betEntity = new BetEntity();
                    betEntity.setName(betDto.getName());
                    betEntity.setBookmaker(bookmakerEntity);

                    if (betDto.getValues() != null) {
                        List<ValueEntity> valueEntities = betDto.getValues().stream()
                                .map(oddValueDto -> mapToEntity(oddValueDto, betEntity, fixtureId))
                                .toList();
                        betEntity.setValues(valueEntities);
                    }

                    return betEntity;
                });
    }

    private ValueEntity mapToEntity(@NotNull OddValueDto oddValueDto, BetEntity betEntity, String fixtureId) {
        ValueEntity valueEntity = new ValueEntity();
        valueEntity.setValue(oddValueDto.getValue());
        valueEntity.setOdd(oddValueDto.getOdd());

        valueEntity.setFixtureId(parseLong(fixtureId));

        valueEntity.setBet(betEntity);
        return valueEntity;
    }
}
