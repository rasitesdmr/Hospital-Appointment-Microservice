package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.HospitalClinicRequest;
import kafka.model.dto.response.HospitalClinicResponse;

public interface HospitalClinicRelationshipService {
    HospitalClinicResponse associateHospitalWithClinic (HospitalClinicRequest hospitalClinicRequest);
}
