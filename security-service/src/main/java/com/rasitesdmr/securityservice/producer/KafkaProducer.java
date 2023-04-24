package com.rasitesdmr.securityservice.producer;

import kafka.model.dto.request.UserKafkaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserToKafka (UserKafkaRequest userKafkaRequest) {
        kafkaTemplate.send("user-topic", 0, "key-1", userKafkaRequest);
    }


}