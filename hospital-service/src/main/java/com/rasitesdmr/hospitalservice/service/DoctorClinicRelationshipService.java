package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.DoctorClinicRequest;
import kafka.model.dto.response.DoctorClinicResponse;

import java.util.List;

public interface DoctorClinicRelationshipService {

    DoctorClinicResponse associateDoctorWithClinic(DoctorClinicRequest doctorClinicRequest);
    void excelToAssociateDoctorWithClinic(List<DoctorClinicResponse> doctorClinicResponseList);
}
