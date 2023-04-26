package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.CityRequest;
import kafka.model.dto.response.CityResponse;

public interface CityService {
    CityResponse createCity (CityRequest cityRequest);
}
