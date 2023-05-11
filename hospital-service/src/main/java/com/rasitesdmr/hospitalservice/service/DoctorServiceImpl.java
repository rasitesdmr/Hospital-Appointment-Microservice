package com.rasitesdmr.hospitalservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.hospitalservice.exception.AlreadyAvailableException;
import com.rasitesdmr.hospitalservice.exception.BadRequestException;
import com.rasitesdmr.hospitalservice.exception.RegistrationException;
import com.rasitesdmr.hospitalservice.repository.ClinicRepository;
import com.rasitesdmr.hospitalservice.repository.DoctorRepository;
import kafka.model.Clinic;
import kafka.model.Doctor;
import kafka.model.dto.request.DoctorRequest;
import kafka.model.dto.response.DoctorResponse;
import kafka.model.dto.response.HospitalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final ClinicRepository clinicRepository;

    @Override
    public DoctorResponse createDoctor(DoctorRequest doctorRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        int countAllClinics  =  clinicRepository.findAll().size();
        if (countAllClinics == 0){
            log.error("[Metot : {}] - Doktor eklerken , klinik tablosunda en az 1 tane veri olmalıdır",methodName);
            throw new BadRequestException("Doktor eklerken , klinik tablosunda en az 1 tane veri olmalıdır");
        }

        boolean doctorExists = doctorRepository.existsById(doctorRequest.getIdentityNumber());
        if (doctorExists){
            log.error("[Metot : {}] - {} kimlik numarasına sahip doktor zaten mevcut", methodName,doctorRequest.getIdentityNumber());
            throw  new AlreadyAvailableException("Doktor zaten mevcut : " + doctorRequest.getIdentityNumber());
        }
        Doctor doctor = new Doctor();
        try {
            doctor.setIdentityNumber(doctorRequest.getIdentityNumber());
            doctor.setFirstName(doctorRequest.getFirstName().toUpperCase());
            doctor.setLastName(doctorRequest.getLastName().toUpperCase());
            doctor.setEmail(doctorRequest.getEmail());
            doctor.setDateOfBirth(doctorRequest.getDateOfBirth());
            doctor.setPhoneNumber(doctorRequest.getPhoneNumber());
            doctor.setProfession(doctorRequest.getProfession());
            doctorRepository.save(doctor);
        }catch (Exception exception){
            log.error("[Metot : - {}] - {} kimlik numarasına sahip doktor varlığı kaydedilirken hata oluştu : {}",methodName,doctorRequest.getIdentityNumber(),exception.getMessage());
            throw new RegistrationException(doctor.getIdentityNumber() + " kimlik numarasına sahip doktor varlığı kaydedilirken hata oluştu : " + exception.getMessage());
        }

        return DoctorResponse.builder()
                .identityNumber(doctor.getIdentityNumber())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .email(doctor.getEmail())
                .dateOfBirth(doctor.getDateOfBirth())
                .phoneNumber(doctor.getPhoneNumber())
                .profession(doctor.getProfession())
                .build();
    }

    @Override
    public void createExcelDoctor(List<DoctorResponse> doctorResponseList) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        ObjectMapper mapper = new ObjectMapper();
        List<DoctorResponse> doctorList = mapper.convertValue(doctorResponseList, new TypeReference<List<DoctorResponse>>() {});

        for (DoctorResponse doctorResponse : doctorList){

            boolean doctorExists = doctorRepository.existsById(doctorResponse.getIdentityNumber());
            if (doctorExists){
                log.error("[Metot : {}] - {} kimlik numarasına sahip doktor zaten mevcut", methodName,doctorResponse.getIdentityNumber());
            }else {
                Doctor doctor = new Doctor();
                try {
                    doctor.setIdentityNumber(doctorResponse.getIdentityNumber());
                    doctor.setFirstName(doctorResponse.getFirstName().toUpperCase());
                    doctor.setLastName(doctorResponse.getLastName().toUpperCase());
                    doctor.setEmail(doctorResponse.getEmail());
                    doctor.setDateOfBirth(doctorResponse.getDateOfBirth());
                    doctor.setPhoneNumber(doctorResponse.getPhoneNumber());
                    doctor.setProfession(doctorResponse.getProfession());
                    doctorRepository.save(doctor);
                    log.info("[Metot : {}] - {} kimlik numarasına sahip doktor varlığı kaydedildi. ",methodName,doctorResponse.getIdentityNumber());
                }catch (Exception exception){
                    log.error("[Metot :  {}] - {} kimlik numarasına sahip doktor varlığı kaydedilirken hata oluştu : {}",methodName,doctorResponse.getIdentityNumber(),exception.getMessage());
                }
            }
        }
    }

    @Override
    public List<DoctorResponse> getDoctorListByClinicName(String clinicName) {
        Clinic clinic = clinicRepository.findByName(clinicName);
        Set<Doctor> doctorList = clinic.getDoctors();
        List<DoctorResponse> doctorResponseList = new ArrayList<>();
        for (Doctor doctor : doctorList){
            DoctorResponse doctorResponse = new DoctorResponse();
            doctorResponse.setIdentityNumber(doctor.getIdentityNumber());
            doctorResponse.setFirstName(doctor.getFirstName());
            doctorResponse.setLastName(doctor.getLastName());
            doctorResponse.setEmail(doctor.getEmail());
            doctorResponse.setProfession(doctor.getProfession());
            doctorResponse.setPhoneNumber(doctor.getPhoneNumber());
            doctorResponse.setDateOfBirth(doctor.getDateOfBirth());
            doctorResponseList.add(doctorResponse);
        }
    return doctorResponseList;
    }
}
