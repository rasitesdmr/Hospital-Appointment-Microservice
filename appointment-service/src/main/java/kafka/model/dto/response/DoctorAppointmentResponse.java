package kafka.model.dto.response;

import kafka.model.Appointment;


import java.util.List;

public class DoctorAppointmentResponse {

    List<Appointment> appointmentList;

    public DoctorAppointmentResponse(){

    }

    public DoctorAppointmentResponse(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
