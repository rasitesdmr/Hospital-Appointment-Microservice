package com.rasitesdmr.appointmentservice.repository;

import kafka.model.dto.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
}
