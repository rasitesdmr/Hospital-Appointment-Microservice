package kafka.model.dto.response;

import com.rasitesdmr.appointmentservice.enums.EStatus;

public class AppointmentResponse {

    private Long id;
    private String status;
    private CityResponse cityResponse;
    private ClinicResponse clinicResponse;
    private DoctorResponse doctorResponse;
    private HospitalResponse hospitalResponse;

    private PatientResponse patientResponse;
    private String appointmentTime;
    private String appointmentDate;

}
