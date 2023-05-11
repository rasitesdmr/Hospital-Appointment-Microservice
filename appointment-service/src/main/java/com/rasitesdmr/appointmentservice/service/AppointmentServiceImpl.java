package com.rasitesdmr.appointmentservice.service;

import com.rasitesdmr.appointmentservice.enums.EStatus;
import com.rasitesdmr.appointmentservice.repository.AppointmentRepository;
import com.rasitesdmr.appointmentservice.repository.PatientRepository;
import jakarta.servlet.http.HttpServletRequest;
import kafka.model.dto.Appointment;
import kafka.model.dto.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void appointmentMakingProcess(long cityId, long hospitalId, long clinicId, String doctorIdentityNumber, String appointmentTime, String appointmentDate) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String loggedInUserIdentityNumber = request.getHeader("identityNumber");

        Patient patient = patientRepository.findById(loggedInUserIdentityNumber).get();

        Appointment appointment = new Appointment();
        appointment.setCityId(cityId);
        appointment.setHospitalId(hospitalId);
        appointment.setClinicId(clinicId);
        appointment.setDoctorIdentityNumber(doctorIdentityNumber);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStatus(EStatus.ACTIVE);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
        System.out.println(appointment.toString());
    }
}
