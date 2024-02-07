package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.countries.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryEntityRepository extends JpaRepository<CountryEntity, String> {

    Optional<CountryEntity> findByCode(String code);

    Optional<CountryEntity>  findByName(String name);
}