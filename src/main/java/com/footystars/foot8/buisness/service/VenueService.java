package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.venue.model.VenueDto;
import com.footystars.foot8.persistence.entities.venues.Venue;
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
    public void updateFromDto(@NotNull VenueDto venueDto) {
        if (venueDto.getVenueId() != null) {
            var id = venueDto.getVenueId();
            var venue = venueRepository.findById(id);
            if (venue.isPresent()) {
                var venueEntity = venue.get();
                venueMapper.partialUpdate(venueDto, venueEntity);
                saveVenue(venueEntity);
            } else {
                var newVenue = venueMapper.toEntity(venueDto);
                saveVenue(newVenue);
            }
        }
    }

    public Optional<Venue> findById(@NotNull Long venueId) {
        return venueRepository.findById(venueId);
    }

    public void saveVenue(@NotNull Venue venueEntity) {
        venueRepository.save(venueEntity);
    }

}
