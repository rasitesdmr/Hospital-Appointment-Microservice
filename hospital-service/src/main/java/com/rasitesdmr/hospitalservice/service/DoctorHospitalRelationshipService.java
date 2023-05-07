package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.DoctorHospitalRequest;
import kafka.model.dto.response.DoctorHospitalResponse;

import java.util.List;

public interface DoctorHospitalRelationshipService {
    DoctorHospitalResponse associateDoctorWithHospital(DoctorHospitalRequest doctorHospitalRequest);
    void excelToAssociateDoctorWithHospital(List<DoctorHospitalResponse> doctorHospitalResponseList);
}
