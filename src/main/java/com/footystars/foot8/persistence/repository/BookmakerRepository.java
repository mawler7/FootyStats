package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.odds.bookmakers.BookmakerEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BookmakerRepository extends JpaRepository<BookmakerEntity, Long> {

    @NotNull
    Optional<BookmakerEntity> findById(@NotNull Long id);
}
