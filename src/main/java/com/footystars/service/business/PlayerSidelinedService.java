package com.footystars.service.business;

import com.footystars.model.entity.PlayerSidelined;
import com.footystars.persistence.repository.PlayerSidelinedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class responsible for managing player sidelined (injuries, suspensions) data.
 */
@Service
@RequiredArgsConstructor
public class PlayerSidelinedService {

    private final PlayerSidelinedRepository playerSidelinedRepository;

    /**
     * Finds a player's sidelined record based on player ID and sidelined period (start and end dates).
     *
     * @param playerId The unique identifier of the player.
     * @param started  The start date of the sidelined period.
     * @param ended    The end date of the sidelined period.
     * @return An {@link Optional} containing the sidelined record if found.
     */
    public Optional<PlayerSidelined> findByPlayerIdSeasonAndLeague(Long playerId, String started, String ended) {
        return playerSidelinedRepository.findByPlayerIdAndSidelinedDates(playerId, started, ended);
    }

    /**
     * Saves a player's sidelined (injury or suspension) record to the database.
     *
     * @param playerSidelined The sidelined entity to save.
     */
    @Transactional
    public void save(PlayerSidelined playerSidelined) {
        playerSidelinedRepository.save(playerSidelined);
    }

}
