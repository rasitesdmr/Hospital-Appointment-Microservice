package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.CityRequest;
import kafka.model.dto.response.CityResponse;

import java.util.List;

public interface CityService {
    CityResponse createCity (CityRequest cityRequest);

    void createExcelCity(List<CityResponse> cityResponseList);
}
