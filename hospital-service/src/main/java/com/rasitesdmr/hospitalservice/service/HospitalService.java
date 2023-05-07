package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.HospitalRequest;
import kafka.model.dto.response.HospitalResponse;

import java.util.List;

public interface HospitalService {

    HospitalResponse createHospital (HospitalRequest hospitalRequest);
    void createExcelHospital(List<HospitalResponse> hospitalResponseList);
}
