package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.bookmakers.bookmaker.Bookmaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BookmakerRepository extends JpaRepository<Bookmaker, Long> {
}
