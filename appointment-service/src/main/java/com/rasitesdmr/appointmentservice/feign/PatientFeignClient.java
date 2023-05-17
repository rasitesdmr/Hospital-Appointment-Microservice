package com.rasitesdmr.appointmentservice.feign;

import kafka.model.dto.response.CityResponse;
import kafka.model.dto.response.ClinicResponse;
import kafka.model.dto.response.DoctorResponse;
import kafka.model.dto.response.HospitalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "HOSPITAL-SERVICE")
public interface PatientFeignClient {
    @GetMapping(value = "/city/getCityResponse", consumes = MediaType.APPLICATION_JSON_VALUE)
    CityResponse getCityResponse(@RequestParam(value = "cityId") Long cityId);

    @GetMapping(value = "/clinic/getClinicResponse", consumes = MediaType.APPLICATION_JSON_VALUE)
    ClinicResponse getClinicResponse(@RequestParam(value = "clinicId")Long clinicId);
    @GetMapping(value = "/doctor/getDoctorResponse", consumes = MediaType.APPLICATION_JSON_VALUE)
    DoctorResponse getDoctorResponse(@RequestParam(value = "doctorIdentityNumber")String  doctorIdentityNumber);

    @GetMapping(value = "/hospital/getHospitalResponse", consumes = MediaType.APPLICATION_JSON_VALUE)
    HospitalResponse getHospitalResponse(@RequestParam(value = "hospitalId")Long hospitalId);
}
