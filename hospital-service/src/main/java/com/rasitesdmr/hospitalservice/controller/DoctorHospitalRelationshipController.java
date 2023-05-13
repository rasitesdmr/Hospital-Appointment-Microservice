package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.DoctorHospitalRelationshipService;
import kafka.model.dto.request.DoctorHospitalRequest;
import kafka.model.dto.response.DoctorHospitalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctorHospitalRelationship")
@RequiredArgsConstructor
public class DoctorHospitalRelationshipController {

    private final DoctorHospitalRelationshipService doctorHospitalRelationshipService;

    @PostMapping("/associateDoctorWithHospital")
    public ResponseEntity<DoctorHospitalResponse> associateDoctorWithHospital(@RequestBody DoctorHospitalRequest doctorHospitalRequest){
        return new ResponseEntity<>(doctorHospitalRelationshipService.associateDoctorWithHospital(doctorHospitalRequest), HttpStatus.CREATED);
    }
}
