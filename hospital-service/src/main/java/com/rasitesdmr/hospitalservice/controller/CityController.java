package com.rasitesdmr.hospitalservice.controller;

import com.rasitesdmr.hospitalservice.service.CityService;
import kafka.model.dto.request.CityRequest;
import kafka.model.dto.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping("/createCity")
    public ResponseEntity<CityResponse> createCity(@RequestBody CityRequest cityRequest) {
        return new ResponseEntity<>(cityService.createCity(cityRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getCityList")
    public ResponseEntity<List<CityResponse>> getCityList(){  // Randevu sistemi için şehirleri çeker.
        return new ResponseEntity<>(cityService.getCityList(),HttpStatus.OK);
    }


}
