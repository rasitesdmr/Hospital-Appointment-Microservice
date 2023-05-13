package com.rasitesdmr.appointmentservice.repository;

import com.rasitesdmr.appointmentservice.enums.EStatus;
import kafka.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
    Appointment findByStatus(EStatus status);
    boolean existsByClinicIdAndAppointmentDateAndAppointmentTime(long clinicId , String appointmentDate , String appointmentTime);
}
