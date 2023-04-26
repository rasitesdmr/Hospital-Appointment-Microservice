package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.ClinicService;
import kafka.model.dto.request.ClinicRequest;
import kafka.model.dto.response.ClinicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping("/createClinic")
    public ResponseEntity<ClinicResponse> createClinic(@RequestBody ClinicRequest clinicRequest) {
        return new ResponseEntity<>(clinicService.createClinic(clinicRequest),HttpStatus.CREATED);
    }
}
