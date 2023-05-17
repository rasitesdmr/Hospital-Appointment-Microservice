package kafka.model.dto.response;

import kafka.model.Appointment;
import lombok.Builder;

import java.util.List;
@Builder
public class PatientAppointmentResponse {
    private String identityNumber;
    private String firstName;
    private String lastName;
    private String email;

    private List<AppointmentResponse> appointmentResponses;

    public PatientAppointmentResponse() {
    }

    public PatientAppointmentResponse(String identityNumber, String firstName, String lastName, String email, List<AppointmentResponse> appointmentResponses) {
        this.identityNumber = identityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.appointmentResponses = appointmentResponses;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AppointmentResponse> getAppointmentResponses() {
        return appointmentResponses;
    }

    public void setAppointmentResponses(List<AppointmentResponse> appointmentResponses) {
        this.appointmentResponses = appointmentResponses;
    }
}
