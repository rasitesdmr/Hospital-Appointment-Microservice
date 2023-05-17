package kafka.model.dto.response;

import com.rasitesdmr.appointmentservice.enums.EStatus;

public class AppointmentResponse {

    private Long id;
    private String status;
    private CityResponse cityResponse;
    private ClinicResponse clinicResponse;
    private DoctorResponse doctorResponse;
    private HospitalResponse hospitalResponse;

    private String appointmentTime;
    private String appointmentDate;

    public AppointmentResponse() {
    }

    public AppointmentResponse(Long id, String status, CityResponse cityResponse, ClinicResponse clinicResponse, DoctorResponse doctorResponse, HospitalResponse hospitalResponse, String appointmentTime, String appointmentDate) {
        this.id = id;
        this.status = status;
        this.cityResponse = cityResponse;
        this.clinicResponse = clinicResponse;
        this.doctorResponse = doctorResponse;
        this.hospitalResponse = hospitalResponse;
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CityResponse getCityResponse() {
        return cityResponse;
    }

    public void setCityResponse(CityResponse cityResponse) {
        this.cityResponse = cityResponse;
    }

    public ClinicResponse getClinicResponse() {
        return clinicResponse;
    }

    public void setClinicResponse(ClinicResponse clinicResponse) {
        this.clinicResponse = clinicResponse;
    }

    public DoctorResponse getDoctorResponse() {
        return doctorResponse;
    }

    public void setDoctorResponse(DoctorResponse doctorResponse) {
        this.doctorResponse = doctorResponse;
    }

    public HospitalResponse getHospitalResponse() {
        return hospitalResponse;
    }

    public void setHospitalResponse(HospitalResponse hospitalResponse) {
        this.hospitalResponse = hospitalResponse;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
