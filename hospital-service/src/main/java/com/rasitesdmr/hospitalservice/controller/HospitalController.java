package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.HospitalService;
import kafka.model.dto.request.HospitalRequest;
import kafka.model.dto.response.DoctorResponse;
import kafka.model.dto.response.HospitalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping("/createHospital")
    public ResponseEntity<HospitalResponse> createHospital(@RequestBody HospitalRequest hospitalRequest) {
        return new ResponseEntity<>(hospitalService.createHospital(hospitalRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getHospitalListByCityName")  // Randevu sistemi için şehir adına göre hastaneleri getirir.
    public ResponseEntity<List<HospitalResponse>> getHospitalListByCityName(@RequestParam(name = "cityName") String cityName) {
        return new ResponseEntity<>(hospitalService.getHospitalListByCityName(cityName), HttpStatus.OK);
    }

    @GetMapping(path = "/getHospitalResponse")
    public ResponseEntity<HospitalResponse> getHospitalResponse(@RequestParam(value = "hospitalId")Long hospitalId){
        return new ResponseEntity<>(hospitalService.getHospitalResponse(hospitalId),HttpStatus.OK);
    }
}
