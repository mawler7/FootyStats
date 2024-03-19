package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.bookmakers.bet.Bet;
import com.footystars.foot8.persistence.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;

    public Optional<Bet> findById(Long id) {
        return betRepository.findById(id);
    }
}
