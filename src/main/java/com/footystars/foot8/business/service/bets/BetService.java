package com.footystars.foot8.business.service.bets;

import com.footystars.foot8.business.model.entity.Bet;
import com.footystars.foot8.repository.BetRepository;
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
    public Bet save(Bet bet) {
        return betRepository.save(bet);
    }

}