package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.DoctorService;
import kafka.model.dto.request.DoctorRequest;
import kafka.model.dto.response.DoctorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/createDoctor")
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorRequest doctorRequest) {
        return new ResponseEntity<>(doctorService.createDoctor(doctorRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getDoctorListByClinicName")  // Randevu sistemi için klinik adına göre doktorları getirir.
    public ResponseEntity<List<DoctorResponse>> getDoctorListByClinicName(@RequestParam(name = "clinicName") String clinicName) {
        return new ResponseEntity<>(doctorService.getDoctorListByClinicName(clinicName), HttpStatus.OK);
    }

}
