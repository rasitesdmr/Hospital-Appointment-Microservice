package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.ClinicService;
import kafka.model.dto.request.ClinicRequest;
import kafka.model.dto.response.ClinicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinic")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping("/createClinic")
    public ResponseEntity<ClinicResponse> createClinic(@RequestBody ClinicRequest clinicRequest) {
        return new ResponseEntity<>(clinicService.createClinic(clinicRequest),HttpStatus.CREATED);
    }
    @GetMapping("/getClinicListByHospitalName")  // Randevu sistemi için hastane adına göer klinikleri getirir
    public ResponseEntity<List<ClinicResponse>> getClinicListByHospitalName(@RequestParam(name = "hospitalName") String hospitalName) {
        return new ResponseEntity<>(clinicService.getClinicListByHospitalName(hospitalName), HttpStatus.OK);
    }
}
