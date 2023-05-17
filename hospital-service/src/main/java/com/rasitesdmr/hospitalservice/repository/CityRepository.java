package com.rasitesdmr.hospitalservice.repository;

import kafka.model.City;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByName(String name);

    City findTopByOrderByIdDesc();
    City findByName(String cityName);
}
