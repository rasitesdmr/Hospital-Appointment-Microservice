package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.DoctorClinicRelationshipService;
import kafka.model.dto.request.DoctorClinicRequest;
import kafka.model.dto.response.DoctorClinicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctorClinicRelationship")
@RequiredArgsConstructor
public class DoctorClinicRelationshipController {

    private final DoctorClinicRelationshipService doctorClinicRelationshipService;

    @PostMapping("/associateDoctorWithClinic")
    public ResponseEntity<DoctorClinicResponse> associateDoctorWithClinic(@RequestBody DoctorClinicRequest doctorClinicRequest){
        return new ResponseEntity<>(doctorClinicRelationshipService.associateDoctorWithClinic(doctorClinicRequest), HttpStatus.CREATED);
    }
}
