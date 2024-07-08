package com.footystars.foot8.mapper;


import com.footystars.foot8.business.model.entity.Player;

public interface PlayerPositionStrategy {
    void process(PlayerPosition playerPosition, Player player);
    PlayerPositionStrategy getStrategy(PlayerPosition playerPosition);
}