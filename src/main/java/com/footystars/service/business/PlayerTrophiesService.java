package com.footystars.service.business;

import com.footystars.model.entity.PlayerTrophy;
import com.footystars.persistence.repository.PlayerTrophyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class responsible for managing player trophies data.
 */
@Service
@RequiredArgsConstructor
public class PlayerTrophiesService {

    private final PlayerTrophyRepository playerTrophyRepository;

    /**
     * Finds a player's trophy record based on player ID, season, and league.
     *
     * @param playerId The unique identifier of the player.
     * @param season   The season in which the trophy was won.
     * @param league   The league in which the trophy was won.
     * @return An {@link Optional} containing the trophy record if found.
     */
    public Optional<PlayerTrophy> findByPlayerIdSeasonAndLeague(Long playerId, String season, String league) {
        return playerTrophyRepository.findByPlayerIdAndTrophy(playerId, season, league);
    }

    /**
     * Saves a player's trophy record to the database.
     *
     * @param playerTrophy The trophy entity to save.
     */
    @Transactional
    public void save(PlayerTrophy playerTrophy) {
        playerTrophyRepository.save(playerTrophy);
    }

}
