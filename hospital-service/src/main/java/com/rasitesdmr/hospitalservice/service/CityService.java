package com.rasitesdmr.hospitalservice.service;

import kafka.model.City;
import kafka.model.dto.request.CityRequest;
import kafka.model.dto.response.CityResponse;

import java.util.List;

public interface CityService {
    CityResponse createCity (CityRequest cityRequest);

    void createExcelCity(List<CityResponse> cityResponseList);

    List<CityResponse> getCityList();

    CityResponse getCityResponse(Long cityId);

}
