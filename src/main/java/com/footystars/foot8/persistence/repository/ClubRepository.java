package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entity.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
