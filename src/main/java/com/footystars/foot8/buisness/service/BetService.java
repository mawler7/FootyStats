package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.odds.bets.BetEntity;
import com.footystars.foot8.persistence.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;

    public Optional<BetEntity> findById(Long id) {
        return betRepository.findById(id);
    }
}
