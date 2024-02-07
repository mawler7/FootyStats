package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.odds.bookmakers.BookmakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookmakerRepository extends JpaRepository<BookmakerEntity, Long> {

    Optional<BookmakerEntity> findById(Long id);
}
