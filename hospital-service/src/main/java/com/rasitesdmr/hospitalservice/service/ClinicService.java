package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.ClinicRequest;
import kafka.model.dto.response.ClinicResponse;

import java.util.List;

public interface ClinicService {

    ClinicResponse createClinic(ClinicRequest clinicRequest);
    void createExcelClinic(List<ClinicResponse> clinicResponseList);

    List<ClinicResponse> getClinicListByHospitalName(String hospitalName);
}
