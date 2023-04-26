package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.HospitalClinicRelationshipService;
import kafka.model.dto.request.HospitalClinicRequest;
import kafka.model.dto.response.HospitalClinicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospitalClinicRelationship")
@RequiredArgsConstructor
public class HospitalClinicRelationshipController {

    private final HospitalClinicRelationshipService hospitalClinicRelationshipService;

    @PostMapping("/associateHospitalWithClinic")
    public ResponseEntity<HospitalClinicResponse> associateHospitalWithClinic(@RequestBody HospitalClinicRequest hospitalClinicRequest){
        return new ResponseEntity<>(hospitalClinicRelationshipService.associateHospitalWithClinic(hospitalClinicRequest), HttpStatus.CREATED);
    }
}
