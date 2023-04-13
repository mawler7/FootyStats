package com.example.foot8.persistence.repository;

import com.example.foot8.buisness.venue.model.VenueDto;
import com.example.foot8.persistence.entities.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<VenueEntity, Long> {
}
