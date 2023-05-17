package com.rasitesdmr.hospitalservice.repository;

import kafka.model.Doctor;
import kafka.model.DoctorContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,String> {


}
