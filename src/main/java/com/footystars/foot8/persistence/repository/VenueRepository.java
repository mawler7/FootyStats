package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.venues.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
