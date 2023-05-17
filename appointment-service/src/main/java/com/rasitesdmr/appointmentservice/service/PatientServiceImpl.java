package com.rasitesdmr.appointmentservice.service;

import com.rasitesdmr.appointmentservice.enums.EStatus;
import com.rasitesdmr.appointmentservice.feign.PatientFeignClient;
import com.rasitesdmr.appointmentservice.repository.PatientRepository;
import jakarta.servlet.http.HttpServletRequest;
import kafka.model.Appointment;
import kafka.model.Patient;
import kafka.model.dto.request.UserKafkaRequest;
import kafka.model.dto.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientFeignClient patientFeignClient;

    public PatientServiceImpl(PatientRepository patientRepository, PatientFeignClient patientFeignClient) {
        this.patientRepository = patientRepository;
        this.patientFeignClient = patientFeignClient;
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
        log.info("[Method : {}] - {} kimlik numarasına sahip hasta db'ye kaydedildi", methodName, userKafkaRequest.getIdentityNumber());
    }

    @Override
    public List<PatientAppointmentResponse> getActivePatientAppointmentList() {
        Patient patient = patientRepository.findById(getLoggedInUserInfo()).get();
        List<Appointment> appointmentList = patient.getAppointment().stream().filter(appointment -> appointment.getStatus().equals(EStatus.ACTIVE)).toList();
        List<PatientAppointmentResponse> patientAppointmentResponses = new ArrayList<>();
        for (Appointment appointment : appointmentList) {

            CityResponse cityResponse = patientFeignClient.getCityResponse(appointment.getCityId());
            HospitalResponse hospitalResponse = patientFeignClient.getHospitalResponse(appointment.getHospitalId());
            ClinicResponse clinicResponse = patientFeignClient.getClinicResponse(appointment.getClinicId());
            DoctorResponse doctorResponse = patientFeignClient.getDoctorResponse(appointment.getDoctorIdentityNumber());

            AppointmentResponse appointmentResponse = new AppointmentResponse();
            appointmentResponse.setId(appointment.getId());
            appointmentResponse.setStatus(appointment.getStatus().name());
            appointmentResponse.setCityResponse(cityResponse);
            appointmentResponse.setClinicResponse(clinicResponse);
            appointmentResponse.setHospitalResponse(hospitalResponse);
            appointmentResponse.setDoctorResponse(doctorResponse);


            appointmentResponse.setAppointmentTime(appointment.getAppointmentTime());
            appointmentResponse.setAppointmentDate(appointment.getAppointmentDate());

        }
        return null;
    }

    public String getLoggedInUserInfo() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader("identityNumber");
    }


}
