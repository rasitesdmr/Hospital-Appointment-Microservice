package com.rasitesdmr.hospitalservice.service;

import com.rasitesdmr.hospitalservice.exception.NotAvailableException;
import com.rasitesdmr.hospitalservice.repository.ClinicRepository;
import com.rasitesdmr.hospitalservice.repository.DoctorRepository;
import com.rasitesdmr.hospitalservice.repository.HospitalRepository;
import kafka.model.Clinic;
import kafka.model.Hospital;
import kafka.model.dto.request.HospitalClinicRequest;
import kafka.model.dto.response.HospitalClinicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class HospitalClinicRelationshipServiceImpl implements HospitalClinicRelationshipService {
    private final HospitalRepository hospitalRepository;

    private final ClinicRepository clinicRepository;


    @Override
    public HospitalClinicResponse associateHospitalWithClinic(HospitalClinicRequest hospitalClinicRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        boolean hospitalExists = hospitalRepository.existsById(hospitalClinicRequest.getHospitalId());
        if (!hospitalExists){
            log.error("[Metot : {}] - {} id numaralı hastane mevcut değil", methodName,hospitalClinicRequest.getHospitalId());
            throw new NotAvailableException("Hastane mevcut değil : " + hospitalClinicRequest.getHospitalId());
        }

        boolean clinicExists = clinicRepository.existsById(hospitalClinicRequest.getClinicId());
        if (!clinicExists){
            log.error("[Metot : {}] - {} id numaralı klinik mevcut değil", methodName,hospitalClinicRequest.getClinicId());
            throw new NotAvailableException("Klinik mevcut değil : " + hospitalClinicRequest.getClinicId());
        }

        Hospital hospital = hospitalRepository.findById(hospitalClinicRequest.getHospitalId()).get();
        Clinic clinic = clinicRepository.findById(hospitalClinicRequest.getClinicId()).get();

        if (hospital.getClinics().stream().noneMatch(currentClinic -> currentClinic.getId().equals(hospitalClinicRequest.getClinicId()))){
            hospital.getClinics().add(clinic);
            hospitalRepository.save(hospital);
            log.info("[Metot : {}] - {} id'ye sahip hastane varlığındaki klinik listesine, {} id'ye sahip klinik eklendi",methodName,hospitalClinicRequest.getHospitalId(),hospitalClinicRequest.getClinicId());
        }

        if (clinic.getHospitals().stream().noneMatch(currentHospital -> currentHospital.getId().equals(hospitalClinicRequest.getHospitalId()))){
            clinic.getHospitals().add(hospital);
            clinicRepository.save(clinic);
            log.info("[Metot : {}] - {} id'ye sahip klinik varlığındaki hastane listesine, {} id'ye sahip hastane eklendi",methodName,hospitalClinicRequest.getClinicId(),hospitalClinicRequest.getHospitalId());
        }

        return HospitalClinicResponse.builder()
                .hospitalId(hospitalClinicRequest.getHospitalId())
                .clinicId(hospitalClinicRequest.getClinicId())
                .build();
    }
}
