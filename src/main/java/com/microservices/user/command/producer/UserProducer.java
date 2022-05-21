package com.microservices.user.command.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProducer {

    private String userTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserProducer(@Value(value = "${kafka.userTopic}") String userTopic, KafkaTemplate<String, String> kafkaTemplate) {
        this.userTopic = userTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message){
        kafkaTemplate.send(userTopic, UUID.randomUUID().toString(), message);
    }

}
