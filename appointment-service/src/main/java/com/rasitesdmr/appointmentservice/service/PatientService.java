package com.rasitesdmr.appointmentservice.service;

import kafka.model.Patient;
import kafka.model.dto.request.UserKafkaRequest;
import kafka.model.dto.response.PatientAppointmentResponse;

import java.util.List;

public interface PatientService {

    void createPatient(UserKafkaRequest userKafkaRequest);

    List<PatientAppointmentResponse> getActivePatientAppointmentList();

}
