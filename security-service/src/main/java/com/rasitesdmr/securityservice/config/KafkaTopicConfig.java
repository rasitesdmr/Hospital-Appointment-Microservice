package com.rasitesdmr.securityservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createUserTopic() {
        return TopicBuilder
                .name("user-topic")
                .partitions(1)
                .build();
    }
}
