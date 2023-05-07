package com.rasitesdmr.hospitalservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.hospitalservice.exception.NotAvailableException;
import com.rasitesdmr.hospitalservice.repository.ClinicRepository;
import com.rasitesdmr.hospitalservice.repository.DoctorRepository;
import kafka.model.Clinic;
import kafka.model.Doctor;
import kafka.model.dto.request.DoctorClinicRequest;
import kafka.model.dto.response.ClinicResponse;
import kafka.model.dto.response.DoctorClinicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorClinicRelationshipServiceImpl implements DoctorClinicRelationshipService {

    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public DoctorClinicResponse associateDoctorWithClinic(DoctorClinicRequest doctorClinicRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        boolean doctorExists = doctorRepository.existsById(doctorClinicRequest.getDoctorId().toString());
        if (!doctorExists) {
            log.error("[Metot : {}] - {} kimlik numarasına sahip doktor mevcut değil", methodName, doctorClinicRequest.getDoctorId());
            throw new NotAvailableException("Doktor mevcut değil : " + doctorClinicRequest.getDoctorId());
        }

        boolean clinicExists = clinicRepository.existsById(doctorClinicRequest.getClinicId());
        if (!clinicExists) {
            log.error("[Metot : {}] - {} id'ye sahip klinik mevcut değil", methodName, doctorClinicRequest.getClinicId());
            throw new NotAvailableException("Klinik mevcut değil : " + doctorClinicRequest.getClinicId());
        }

        Doctor doctor = doctorRepository.findById(doctorClinicRequest.getDoctorId().toString()).get();
        Clinic clinic = clinicRepository.findById(doctorClinicRequest.getClinicId()).get();
        if (doctor.getClinics().stream().noneMatch(currentClinic -> currentClinic.getId().equals(doctorClinicRequest.getClinicId()))) {
            doctor.getClinics().add(clinic);
            doctorRepository.save(doctor);
            log.info("[Metot : {}] - {} kimlik numarasına sahip doktor varlığındaki klinik listesine, {} id'ye sahip klinik eklendi", methodName, doctorClinicRequest.getDoctorId(), doctorClinicRequest.getClinicId());

        }

        if (clinic.getDoctors().stream().noneMatch(currentDoctor -> currentDoctor.getIdentityNumber().equals(doctorClinicRequest.getDoctorId().toString()))) {
            clinic.getDoctors().add(doctor);
            clinicRepository.save(clinic);
            log.info("[Metot : {}] - {} id'ye sahip klinik varlığındaki doktor listesine, {} id'ye sahip doktor eklendi", methodName, doctorClinicRequest.getClinicId(), doctorClinicRequest.getDoctorId());

        }

        return DoctorClinicResponse.builder()
                .doctorId(doctorClinicRequest.getDoctorId())
                .clinicId(doctorClinicRequest.getClinicId())
                .build();
    }

    @Override
    public void excelToAssociateDoctorWithClinic(List<DoctorClinicResponse> doctorClinicResponseList) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        ObjectMapper mapper = new ObjectMapper();
        List<DoctorClinicResponse> doctorClinicList = mapper.convertValue(doctorClinicResponseList, new TypeReference<List<DoctorClinicResponse>>() {});

        for (DoctorClinicResponse doctorClinicResponse : doctorClinicList) {

            boolean doctorExists = doctorRepository.existsById(String.valueOf(doctorClinicResponse.getDoctorId()));
            boolean clinicExists = clinicRepository.existsById(doctorClinicResponse.getClinicId());

            if (doctorExists) {
                if (clinicExists) {

                    Doctor doctor = doctorRepository.findById(String.valueOf(doctorClinicResponse.getDoctorId())).get();
                    Clinic clinic = clinicRepository.findById(doctorClinicResponse.getClinicId()).get();

                    if (doctor.getClinics().stream().noneMatch(currentClinic -> currentClinic.getId().equals(doctorClinicResponse.getClinicId()))) {
                        doctor.getClinics().add(clinic);
                        doctorRepository.save(doctor);
                        log.info("[Metot : {}] - {} kimlik numarasına sahip doktor varlığındaki klinik listesine, {} id'ye sahip klinik eklendi", methodName, doctorClinicResponse.getDoctorId(), doctorClinicResponse.getClinicId());

                    }
                    if (clinic.getDoctors().stream().noneMatch(currentDoctor -> currentDoctor.getIdentityNumber().equals(doctorClinicResponse.getDoctorId()))) {
                        clinic.getDoctors().add(doctor);
                        clinicRepository.save(clinic);
                        log.info("[Metot : {}] - {} id'ye sahip klinik varlığındaki doktor listesine, {} id'ye sahip doktor eklendi", methodName, doctorClinicResponse.getClinicId(), doctorClinicResponse.getDoctorId());

                    }

                } else {
                    log.error("[Metot : {}] - {} numaralı id'ye sahip klinik bulunamadı", methodName, doctorClinicResponse.getClinicId());
                }
            } else {
                log.error("[Metot : {}] - {} kimlik numaralı doktor bulunamadı", methodName, doctorClinicResponse.getDoctorId());
            }

        }

    }

}
