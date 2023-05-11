package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.DoctorRequest;
import kafka.model.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse createDoctor (DoctorRequest doctorRequest);
    void createExcelDoctor(List<DoctorResponse> doctorResponseList);

    List<DoctorResponse> getDoctorListByClinicName(String clinicName);
}
