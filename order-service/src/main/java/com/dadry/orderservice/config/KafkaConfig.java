package com.dadry.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value("${kafka.topic.orderCreated}")
    private String topic;

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder
                .name(topic)
                .build();
    }
}
