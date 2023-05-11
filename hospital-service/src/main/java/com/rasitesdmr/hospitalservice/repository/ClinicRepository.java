package com.rasitesdmr.hospitalservice.repository;

import kafka.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    boolean existsByName(String name);
    Clinic findByName(String clinicName);

}
