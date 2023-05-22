package com.example.foot8.service;

import com.example.foot8.buisness.venue.model.VenueDto;
import com.example.foot8.persistence.entities.VenueEntity;
import com.example.foot8.persistence.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;

    public void saveVenue(VenueDto venueDto) {
        Optional<VenueEntity> existingVenueOptional = venueRepository.findById(venueDto.getId());
        if (existingVenueOptional.isPresent()) {
            VenueEntity existingVenue = existingVenueOptional.get();
            if (!existingVenue.isEqual(venueDto)) {
                updateExistingVenue(existingVenue, venueDto);
            }
        } else {
            createNewVenue(venueDto);
        }
    }

    private void updateExistingVenue(VenueEntity existingVenue, VenueDto venueDto) {
        setVenuesFields(existingVenue, venueDto);
    }

    private void setVenuesFields(VenueEntity existingVenue, VenueDto venueDto) {
        existingVenue.setCapacity(venueDto.getCapacity());
        existingVenue.setCity(venueDto.getCity());
        existingVenue.setSurface(venueDto.getSurface());
        existingVenue.setAddress(venueDto.getAddress());
        existingVenue.setImage(venueDto.getImage());
        existingVenue.setCountry(venueDto.getCountry());
        existingVenue.setName(venueDto.getName());
        save(existingVenue);
    }

    private void createNewVenue(VenueDto venueDto) {
        VenueEntity newVenue = new VenueEntity();
        setVenuesFields(newVenue, venueDto);
    }

    public void save(VenueEntity venueEntity) {
        venueRepository.save(venueEntity);
    }

    public Optional<VenueEntity> findById(Long id) {
        return venueRepository.findById(id);
    }

}
