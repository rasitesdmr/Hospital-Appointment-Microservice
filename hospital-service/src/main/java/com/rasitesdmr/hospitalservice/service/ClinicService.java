package com.rasitesdmr.hospitalservice.service;

import kafka.model.dto.request.ClinicRequest;
import kafka.model.dto.response.ClinicResponse;

public interface ClinicService {

    ClinicResponse createClinic(ClinicRequest clinicRequest);
}
