package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.fixture.FixtureDto;
import com.footystars.foot8.persistence.entities.fixtures.Fixture;
import com.footystars.foot8.persistence.entities.fixtures.FixtureMapper;
import com.footystars.foot8.persistence.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FixtureService {

    private final FixtureMapper fixtureMapper;
    private final FixtureRepository fixtureRepository;

    private static final Logger logger = LoggerFactory.getLogger(FixtureService.class);

    public void updateFromDto(@NotNull FixtureDto fixtureDto) {
        Long id = fixtureDto.getFixture().getId();
        Optional<Fixture> optionalFixture = fixtureRepository.findById(id);
        if (optionalFixture.isPresent()) {
            Fixture fixture = optionalFixture.get();
            fixtureMapper.partialUpdate(fixtureDto, fixture);
            fixtureRepository.save(fixture);
            logger.info("Updated fixture");
        } else {
            createFromDto(fixtureDto);
        }
        logger.info("Updated fixture with id: " + id);

    }

    public void createFromDto(@NotNull FixtureDto fixtureDto) {
        Fixture fixture = fixtureMapper.toEntity(fixtureDto);
        fixture.setId(fixture.getId());
        fixtureRepository.save(fixture);
        logger.info("Created fixture with id: " + fixture.getId());
    }
}