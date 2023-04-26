package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.HospitalService;
import kafka.model.dto.request.HospitalRequest;
import kafka.model.dto.response.HospitalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping("/createHospital")
    public ResponseEntity<HospitalResponse> createHospital(@RequestBody HospitalRequest hospitalRequest) {
        return new ResponseEntity<>(hospitalService.createHospital(hospitalRequest), HttpStatus.CREATED);
    }
}
