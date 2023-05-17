package com.rasitesdmr.appointmentservice.service;

import com.rasitesdmr.appointmentservice.enums.EStatus;
import com.rasitesdmr.appointmentservice.repository.AppointmentRepository;
import com.rasitesdmr.appointmentservice.repository.PatientRepository;
import jakarta.servlet.http.HttpServletRequest;
import kafka.model.Appointment;
import kafka.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    private final Map<String, Integer> months;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        months = new HashMap<>();
        months.put("01", 31);
        months.put("02", 28);
        months.put("03", 31);
        months.put("04", 30);
        months.put("05", 31);
        months.put("06", 30);
        months.put("07", 31);
        months.put("08", 31);
        months.put("09", 30);
        months.put("10", 31);
        months.put("11", 30);
        months.put("12", 31);
    }
// TODO Gelen tarih ve saat doğrulaması front tarafında gerçekleştirilecek
    @Override
    public String appointmentMakingProcess(long cityId, long hospitalId, long clinicId, String doctorIdentityNumber, String appointmentTime, String appointmentDate) {
            String methodName = "appointmentMakingProcess";
        if (!appointmentRepository.existsByClinicIdAndAppointmentDateAndAppointmentTime(clinicId,appointmentDate,appointmentTime)){
            String[] appointmentDateParts = appointmentDate.split("-");
            if (Integer.parseInt(appointmentDateParts[0])>= 2023){
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = requestAttributes.getRequest();
                String loggedInUserIdentityNumber = request.getHeader("identityNumber");

                Patient patient = patientRepository.findById(loggedInUserIdentityNumber).get();

                boolean checked = checkPatientNoShowAppointmentsBeforeMaking(patient, clinicId);
                if (!checked){
                    return "Aldığınız randevuya gitmediğiniz için ceza yediniz. Bu klinikten randevu alamazsınız";
                }

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
                log.info("[Method : {}] - {} kimlik numarasına sahip hasta başarılı bir şekilde randevu aldı.",methodName,loggedInUserIdentityNumber);
                return "Randevu başarılı bir şekilde alındı";
            }
          return "Eski yıllardan randevu alamazsın";
        }
        return "Bu klinik'de aynı saatte zaten randevun var";
    }

    @Override
    public boolean appointmentRemove(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
        return true;
    }


    private boolean checkPatientNoShowAppointmentsBeforeMaking(Patient patient, long clinicId) {  // Randevu almadan önce hastanın randevularını kontrol etme
       String methodName = "checkPatientNoShowAppointmentsBeforeMaking";
        List<Appointment> appointmentList = patient.getAppointment();
        List<Appointment> cancelledAppointmentList = appointmentList.stream().filter(appointment -> appointment.getStatus().equals(EStatus.PATIENT_DID_NOT_ARRIVE)).toList();
        List<Appointment> appointmentListByClinicId = cancelledAppointmentList.stream().filter(appointment -> appointment.getClinicId() == clinicId).toList();
        for (Appointment appointment : appointmentListByClinicId) {

            String[] appointmentDateParts = appointment.getAppointmentDate().split("-");

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String[] formattedDatePars = formatter.format(date).split("-");

            if (Objects.equals(appointmentDateParts[1], formattedDatePars[1])) {
                int appointmentDay = Integer.parseInt(appointmentDateParts[2]);
                int currentDay = Integer.parseInt(formattedDatePars[2]);
                int difference = Math.abs(currentDay - appointmentDay);
                return difference == 1;
            }

            int intAppointmentMonth = Integer.parseInt(appointmentDateParts[1]);
            int intCurrentMont = Integer.parseInt(formattedDatePars[1]);
            int difference = Math.abs(intCurrentMont - intAppointmentMonth);
            if (difference == 1) {
                int maxNumberOfDays = months.get(appointmentDateParts[1]);
                int numberOfDays = Integer.parseInt(appointmentDateParts[2]);
                int a = Math.abs(maxNumberOfDays - numberOfDays);
                int b = Integer.parseInt(formattedDatePars[2]);
                int sum  = a + b;
               return sum == 1;
            }

        }
        return true;
    }

}
