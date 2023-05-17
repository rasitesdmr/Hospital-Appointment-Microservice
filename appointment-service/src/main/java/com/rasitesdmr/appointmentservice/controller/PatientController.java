package com.rasitesdmr.appointmentservice.controller;

import com.rasitesdmr.appointmentservice.service.PatientService;
import kafka.model.dto.response.PatientAppointmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping(path = "/getActivePatientAppointmentList")
    public ResponseEntity<List<PatientAppointmentResponse>> getActivePatientAppointmentList(){
       return new ResponseEntity<>(patientService.getActivePatientAppointmentList(), HttpStatus.OK);
    }
}
