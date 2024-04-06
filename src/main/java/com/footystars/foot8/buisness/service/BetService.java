package com.footystars.foot8.buisness.service;

import com.footystars.foot8.buisness.model.entity.Bet;
import com.footystars.foot8.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;

    private final Log logger = LogFactory.getLog(BetService.class);

    public Optional<Bet> findById(Long id) {
        return betRepository.findById(id);
    }
    public Bet save(Bet bet) {
        return betRepository.save(bet);
    }

}