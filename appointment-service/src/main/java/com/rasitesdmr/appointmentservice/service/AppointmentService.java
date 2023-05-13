package com.rasitesdmr.appointmentservice.service;

public interface AppointmentService {

   String appointmentMakingProcess (long cityId,
                                  long hospitalId,
                                  long clinicId,
                                  String doctorIdentityNumber,
                                  String appointmentTime,
                                  String appointmentDate);


}
