package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.DoctorHospitalRequest;
import kafka.model.dto.response.DoctorHospitalResponse;

public interface DoctorHospitalRelationshipService {
    DoctorHospitalResponse associateDoctorWithHospital(DoctorHospitalRequest doctorHospitalRequest);
}
