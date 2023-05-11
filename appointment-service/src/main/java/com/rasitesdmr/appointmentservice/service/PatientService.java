package com.rasitesdmr.appointmentservice.service;

import kafka.model.dto.request.UserKafkaRequest;

public interface PatientService {

    void createPatient(UserKafkaRequest userKafkaRequest);
}
