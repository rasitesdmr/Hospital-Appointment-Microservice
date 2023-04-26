package com.rasitesdmr.hospitalservice.service;

import com.rasitesdmr.hospitalservice.exception.AlreadyAvailableException;
import com.rasitesdmr.hospitalservice.exception.BadRequestException;
import com.rasitesdmr.hospitalservice.exception.RegistrationException;
import com.rasitesdmr.hospitalservice.repository.ClinicRepository;
import com.rasitesdmr.hospitalservice.repository.HospitalRepository;
import kafka.model.Clinic;
import kafka.model.dto.request.ClinicRequest;
import kafka.model.dto.response.ClinicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public ClinicResponse createClinic(ClinicRequest clinicRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        int countAllHospital  =  hospitalRepository.findAll().size();
        if (countAllHospital == 0){
            log.error("[Metot : {}] - Klinik eklerken , hastane tablosunda en az 1 tane veri olmalıdır",methodName);
            throw new BadRequestException("Klinik eklerken , hastane tablosunda en az 1 tane veri olmalıdır");
        }

        String clinicName = clinicRequest.getName();
        if (clinicName == null || clinicName.trim().isEmpty()) {
            log.error("[Metot : {}] - Klinik adı boş olmamalı",methodName);
            throw new BadRequestException("Klinik adı boş olmamalı");
        }

        String uppercaseClinicName = clinicName.toUpperCase();

        boolean clinicExists = clinicRepository.existsByName(uppercaseClinicName);
        if (clinicExists){
            log.error("[Metot : {}] - {} adına sahip klinik zaten mevcut", methodName,uppercaseClinicName );
            throw  new AlreadyAvailableException("Klinik zaten mevcut : " + uppercaseClinicName );
        }

        Clinic clinic = new Clinic();
        try {
            clinic.setName(uppercaseClinicName);
            clinicRepository.save(clinic);
            log.info("[Metot : {}] - {} varlığı kaydedildi" ,methodName,clinic.getId());

        }catch (Exception exception){
            log.error("[Metot : - {}] - {} adına sahip klinik varlığını kaydederken hata oluştu : {}",methodName,uppercaseClinicName,exception.getMessage());
            throw new RegistrationException(uppercaseClinicName + "adına sahip klinik varlığını kaydederken hata oluştu");
        }
        return ClinicResponse.builder()
                .id(clinic.getId())
                .name(clinic.getName())
                .build();
    }
}
