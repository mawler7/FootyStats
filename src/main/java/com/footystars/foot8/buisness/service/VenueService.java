package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeasonDto;
import com.footystars.foot8.persistence.entities.teams.team.TeamDto;
import com.footystars.foot8.persistence.entities.venues.Venue;
import com.footystars.foot8.persistence.entities.venues.VenueDto;
import com.footystars.foot8.persistence.entities.venues.VenueMapper;
import com.footystars.foot8.persistence.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Transactional
    public void fetchVenues(@NotNull VenueDto venueDto) {
        if (venueDto.getId() != null) {
            var id = venueDto.getId();
            var optionalVenue = venueRepository.findById(id);
            if (optionalVenue.isPresent()) {
                var venueEntity = optionalVenue.get();
                venueMapper.partialUpdate(venueDto, venueEntity);
                saveVenue(venueEntity);
            } else {
                var newVenue = venueMapper.toEntity(venueDto);
                saveVenue(newVenue);
            }
        }
    }

    public Optional<Venue> findById(@NotNull Long id) {
        return venueRepository.findById(id);
    }

    @Transactional
    public Venue saveVenue(@NotNull Venue venue) {
       return venueRepository.save(venue);
    }



}
