package com.rasitesdmr.appointmentservice.consumer;

import com.rasitesdmr.appointmentservice.service.PatientService;
import kafka.model.dto.request.UserKafkaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    private final PatientService patientService;

    public KafkaConsumer(PatientService patientService) {
        this.patientService = patientService;
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "user-topic",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void userPatientConversion(UserKafkaRequest userKafkaRequest){
        String methodName = "userPatientConversion";
        log.info("[Method : {}] - {} kimlik numarasına sahip hasta kuyruktan alındı" ,methodName,userKafkaRequest.getIdentityNumber());
        patientService.createPatient(userKafkaRequest);
    }

}
