package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.DoctorRequest;
import kafka.model.dto.response.DoctorResponse;

public interface DoctorService {
    DoctorResponse createDoctor (DoctorRequest doctorRequest);
}
