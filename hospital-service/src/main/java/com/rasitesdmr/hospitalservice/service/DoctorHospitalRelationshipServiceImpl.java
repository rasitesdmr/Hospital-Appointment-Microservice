package com.rasitesdmr.hospitalservice.service;

import com.rasitesdmr.hospitalservice.exception.NotAvailableException;
import com.rasitesdmr.hospitalservice.repository.DoctorRepository;
import com.rasitesdmr.hospitalservice.repository.HospitalRepository;
import kafka.model.Doctor;
import kafka.model.Hospital;
import kafka.model.dto.request.DoctorHospitalRequest;
import kafka.model.dto.response.DoctorHospitalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorHospitalRelationshipServiceImpl implements DoctorHospitalRelationshipService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public DoctorHospitalResponse associateDoctorWithHospital(DoctorHospitalRequest doctorHospitalRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        boolean doctorExists = doctorRepository.existsById(doctorHospitalRequest.getDoctorId().toString());
        if (!doctorExists){
            log.error("[Metot : {}] - {} kimlik numarasına sahip doktor mevcut değil", methodName,doctorHospitalRequest.getDoctorId());
            throw new NotAvailableException("Doktor mevcut değil : " + doctorHospitalRequest.getDoctorId());
        }

        boolean hospitalExists = hospitalRepository.existsById(doctorHospitalRequest.getHospitalId());
        if (!hospitalExists){
            log.error("[Metot : {}] - {} id numaralı hastane mevcut değil", methodName,doctorHospitalRequest.getHospitalId());
            throw new NotAvailableException("Hastane mevcut değil : " + doctorHospitalRequest.getHospitalId());
        }

        Doctor doctor = doctorRepository.findById(doctorHospitalRequest.getDoctorId().toString()).get();
        Hospital hospital = hospitalRepository.findById(doctorHospitalRequest.getHospitalId()).get();
        if (doctor.getHospitals().stream().noneMatch(currentHospital -> currentHospital.getId().equals(doctorHospitalRequest.getHospitalId()))){
            doctor.getHospitals().add(hospital);
            doctorRepository.save(doctor);
            log.info("[Metot : {}] - {} kimlik numarasına sahip doktor varlığındaki hastane listesine, {} id'ye sahip hastane eklendi",methodName,doctorHospitalRequest.getDoctorId(),doctorHospitalRequest.getHospitalId());

        }

        if (hospital.getDoctors().stream().noneMatch(currentDoctor -> currentDoctor.getIdentityNumber().equals(doctorHospitalRequest.getDoctorId().toString()))){
            hospital.getDoctors().add(doctor);
            hospitalRepository.save(hospital);
            log.info("[Metot : {}] - {} id'ye sahip hastane varlığındaki doktor listesine, {} id'ye sahip doktor eklendi",methodName,doctorHospitalRequest.getHospitalId(),doctorHospitalRequest.getDoctorId());

        }
        return DoctorHospitalResponse.builder()
                .hospitalId(doctorHospitalRequest.getHospitalId())
                .doctorId(doctorHospitalRequest.getDoctorId())
                .build();
    }
}
