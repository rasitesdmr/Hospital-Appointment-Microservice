package com.rasitesdmr.hospitalservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.hospitalservice.exception.NotAvailableException;
import com.rasitesdmr.hospitalservice.repository.ClinicRepository;
import com.rasitesdmr.hospitalservice.repository.HospitalRepository;
import jakarta.transaction.Transactional;
import kafka.model.Clinic;
import kafka.model.Hospital;
import kafka.model.dto.request.HospitalClinicRequest;
import kafka.model.dto.response.HospitalClinicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    @Transactional
    public void excelToAssociateHospitalWithClinic(List<HospitalClinicResponse> hospitalClinicResponseList) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        ObjectMapper mapper = new ObjectMapper();
        List<HospitalClinicResponse> hospitalClinicList = mapper.convertValue(hospitalClinicResponseList, new TypeReference<List<HospitalClinicResponse>>() {});

        for (HospitalClinicResponse hospitalClinicResponse : hospitalClinicList){
            boolean hospitalExists = hospitalRepository.existsById(hospitalClinicResponse.getHospitalId());
            boolean clinicExists = clinicRepository.existsById(hospitalClinicResponse.getClinicId());

            if (hospitalExists){
                if (clinicExists){

                    Hospital hospital = hospitalRepository.findById(hospitalClinicResponse.getHospitalId()).get();
                    Clinic clinic = clinicRepository.findById(hospitalClinicResponse.getClinicId()).get();

                    if (hospital.getClinics().stream().noneMatch(currentClinic -> currentClinic.getId().equals(hospitalClinicResponse.getClinicId()))){
                        hospital.getClinics().add(clinic);
                        hospitalRepository.save(hospital);
                        log.info("[Metot : {}] - {} id'ye sahip hastane varlığındaki klinik listesine, {} id'ye sahip klinik eklendi",methodName,hospitalClinicResponse.getHospitalId(),hospitalClinicResponse.getClinicId());
                    }

                    if (clinic.getHospitals().stream().noneMatch(currentHospital -> currentHospital.getId().equals(hospitalClinicResponse.getHospitalId()))){
                        clinic.getHospitals().add(hospital);
                        clinicRepository.save(clinic);
                        log.info("[Metot : {}] - {} id'ye sahip klinik varlığındaki hastane listesine, {} id'ye sahip hastane eklendi",methodName,hospitalClinicResponse.getClinicId(),hospitalClinicResponse.getHospitalId());
                    }
                }else {
                    log.error("[Metot : {}] - {} id'ye sahip klinik bulunamadı",methodName,hospitalClinicResponse.getClinicId());
                }
            }else {
                log.error("[Metot : {}] - {} id'ye sahip hastene bulunamadı",methodName,hospitalClinicResponse.getHospitalId());
            }

        }
    }
}
