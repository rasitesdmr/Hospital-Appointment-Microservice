package com.rasitesdmr.excelservice.producer;

import kafka.model.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCityListToQueue (List<CityResponse> cityList) {
        kafkaTemplate.send("excel-topic", 0, "key-1", cityList);
        log.info("cityExcelRequest listesi kuyruğa gönderildi");
    }

    public void sendClinicListToQueue(List<ClinicResponse> clinicList){
        kafkaTemplate.send("excel-topic",1,"key-2",clinicList);
    }

    public void sendHospitalListToQueue(List<HospitalResponse> hospitalList){
        kafkaTemplate.send("excel-topic",2,"key-3", hospitalList);
    }

    public void sendDoctorListToQueue(List<DoctorResponse> doctorList){
        kafkaTemplate.send("excel-topic",3,"key-4", doctorList);
    }

    public void sendDoctorClinicListToQueue(List<DoctorClinicResponse> doctorClinicList){
        kafkaTemplate.send("excel-topic",4,"key-5", doctorClinicList);
    }

    public void sendDoctorHospitalListToQueue(List<DoctorHospitalResponse> doctorHospitalList){
        kafkaTemplate.send("excel-topic",5,"key-5", doctorHospitalList);
    }

    public void sendHospitalClinicListToQueue(List<HospitalClinicResponse> hospitalClinicList){
        kafkaTemplate.send("excel-topic",6,"key-5", hospitalClinicList);
    }

    public void sendDoctorContactInfoListToQueue(List<DoctorResponse> doctorList){
        kafkaTemplate.send("excel-topic",7,"key-5", doctorList);
    }
}