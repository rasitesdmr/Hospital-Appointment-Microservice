package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.DoctorClinicRequest;
import kafka.model.dto.response.DoctorClinicResponse;

public interface DoctorClinicRelationshipService {

    DoctorClinicResponse associateDoctorWithClinic(DoctorClinicRequest doctorClinicRequest);
}
