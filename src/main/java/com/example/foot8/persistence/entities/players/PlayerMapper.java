package com.example.foot8.persistence.entities.players;

import com.example.foot8.api.players.model.player.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerEntity playerToEntity(Player player);

    Player entityToPlayer(PlayerEntity playerEntity);

}