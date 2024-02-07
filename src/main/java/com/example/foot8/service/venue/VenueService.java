package com.example.foot8.service.venue;

import com.example.foot8.api.venue.model.VenueDto;
import com.example.foot8.persistence.entities.venues.VenueEntity;
import com.example.foot8.persistence.entities.venues.VenueMapper;
import com.example.foot8.persistence.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    public VenueEntity saveOrUpdateVenue(@NotNull VenueDto venueDto) {

        if (venueDto.getId() != null) {
            Long id = venueDto.getId();
            Optional<VenueEntity> venue = venueRepository.findById(id);
            if (venue.isPresent()) {
                return venue.get();
            }
        } else {
            VenueEntity venueEntity = venueMapper.toEntity(venueDto);
            return saveVenue(venueEntity);
        }
        return venueMapper.toEntity(venueDto);
    }

    public Optional<VenueEntity> findById(@NotNull Long venueId) {
        return venueRepository.findById(venueId);
    }

    public VenueEntity saveVenue(@NotNull VenueEntity venueEntity) {
        return venueRepository.save(venueEntity);
    }

}
