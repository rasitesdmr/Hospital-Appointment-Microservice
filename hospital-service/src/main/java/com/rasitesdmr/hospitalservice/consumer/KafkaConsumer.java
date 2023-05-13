package com.rasitesdmr.hospitalservice.consumer;

import com.rasitesdmr.hospitalservice.service.*;
import kafka.model.dto.response.*;
import kafka.model.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class KafkaConsumer {

    private final CityService cityService;
    private final ClinicService clinicService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final DoctorClinicRelationshipService doctorClinicRelationshipService;
    private final DoctorHospitalRelationshipService doctorHospitalRelationshipService;
    private final HospitalClinicRelationshipService hospitalClinicRelationshipService;

    public KafkaConsumer(CityService cityService, ClinicService clinicService, HospitalService hospitalService, DoctorService doctorService, DoctorClinicRelationshipService doctorClinicRelationshipService, DoctorHospitalRelationshipService doctorHospitalRelationshipService, HospitalClinicRelationshipService hospitalClinicRelationshipService) {
        this.cityService = cityService;
        this.clinicService = clinicService;
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
        this.doctorClinicRelationshipService = doctorClinicRelationshipService;
        this.doctorHospitalRelationshipService = doctorHospitalRelationshipService;
        this.hospitalClinicRelationshipService = hospitalClinicRelationshipService;
    }


    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "excel-topic",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void readCityListFromQueue(List<CityResponse> cityList){
        cityService.createExcelCity(cityList);
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "excel-topic",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void readClinicListFromQueue(List<ClinicResponse> clinicResponseList){
        clinicService.createExcelClinic(clinicResponseList);
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "excel-topic",
                    partitionOffsets = @PartitionOffset(partition = "2", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void readHospitalListFromQueue(List<HospitalResponse> hospitalResponseList){
        hospitalService.createExcelHospital(hospitalResponseList);
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "excel-topic",
                    partitionOffsets = @PartitionOffset(partition = "3", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void readDoctorListFromQueue(List<DoctorResponse> doctorResponseList){
        doctorService.createExcelDoctor(doctorResponseList);
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "excel-topic",
                    partitionOffsets = @PartitionOffset(partition = "4", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public  void readDoctorClinicListFromQueue(List<DoctorClinicResponse> doctorClinicResponseList){
        doctorClinicRelationshipService.excelToAssociateDoctorWithClinic(doctorClinicResponseList);
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "excel-topic",
                    partitionOffsets = @PartitionOffset(partition = "5", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void readDoctorHospitalListFromQueue(List<DoctorHospitalResponse> doctorHospitalResponseList){
        doctorHospitalRelationshipService.excelToAssociateDoctorWithHospital(doctorHospitalResponseList);
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "excel-topic",
                    partitionOffsets = @PartitionOffset(partition = "6", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void readHospitalClinicListFromQueue(List<HospitalClinicResponse> hospitalClinicResponseList){
        hospitalClinicRelationshipService.excelToAssociateHospitalWithClinic(hospitalClinicResponseList);
    }

}
