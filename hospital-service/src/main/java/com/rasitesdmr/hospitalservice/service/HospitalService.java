package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.HospitalRequest;
import kafka.model.dto.response.HospitalResponse;

public interface HospitalService {

    HospitalResponse createHospital (HospitalRequest hospitalRequest);
}
