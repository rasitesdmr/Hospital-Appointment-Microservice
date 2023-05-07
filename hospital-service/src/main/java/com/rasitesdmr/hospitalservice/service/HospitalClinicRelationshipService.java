package com.rasitesdmr.hospitalservice.service;

import com.google.common.collect.Lists;
import kafka.model.dto.request.HospitalClinicRequest;
import kafka.model.dto.response.HospitalClinicResponse;

import java.util.List;

public interface HospitalClinicRelationshipService {
    HospitalClinicResponse associateHospitalWithClinic (HospitalClinicRequest hospitalClinicRequest);
    void excelToAssociateHospitalWithClinic(List<HospitalClinicResponse> hospitalClinicResponseList);
}
