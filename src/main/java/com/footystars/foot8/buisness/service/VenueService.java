package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.teams.TeamApi;
import com.footystars.foot8.buisness.model.entity.Venue;
import com.footystars.foot8.buisness.model.dto.VenueDto;
import com.footystars.foot8.mapper.VenueMapper;
import com.footystars.foot8.repository.VenueRepository;
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
    public Venue fetchVenue(@NotNull TeamApi teamApi) {
        var venueId = teamApi.getVenue().getId();

        var venueOptional = findById(venueId);
        if (venueOptional.isEmpty()) {
            var venue = venueMapper.toEntity(teamApi.getVenue());
            return venueRepository.save(venue);
        }
        return venueOptional.get();
    }

    @Transactional
    public Venue fetchVenueDto(@NotNull VenueDto venueDto) {
        var venueId = venueDto.getId();

        var venueOptional = findById(venueId);
        if (venueOptional.isEmpty()) {
            var venue = venueMapper.toEntity(venueDto);
            return venueRepository.save(venue);
        }
        return venueOptional.get();
    }

    public Optional<Venue> findById(@NotNull Long id) {
        return venueRepository.findById(id);
    }

}
