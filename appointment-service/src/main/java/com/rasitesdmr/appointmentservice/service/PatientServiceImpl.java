package com.rasitesdmr.appointmentservice.service;

import com.rasitesdmr.appointmentservice.repository.PatientRepository;
import kafka.model.Patient;
import kafka.model.dto.request.UserKafkaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Override
    public void createPatient(UserKafkaRequest userKafkaRequest) {
        String methodName = "createPatient";
        Patient patient = Patient.builder()
                .identityNumber(userKafkaRequest.getIdentityNumber())
                .firstName(userKafkaRequest.getFirstName())
                .lastName(userKafkaRequest.getLastName())
                .email(userKafkaRequest.getEmail())
                .build();
        patientRepository.save(patient);
        log.info("[Method : {}] - {} kimlik numarasına sahip hasta db'ye kaydedildi",methodName,userKafkaRequest.getIdentityNumber());
    }
}
