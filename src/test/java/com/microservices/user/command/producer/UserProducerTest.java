package com.microservices.user.command.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.control.MappingControl;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private UserProducer userProducer;

    @BeforeEach
    public void setUp(){
        userProducer = new UserProducer("user.topic", kafkaTemplate);
    }

    @Test
    void send() {
        SettableListenableFuture<SendResult<String, String>> future = new SettableListenableFuture<>();
        when(kafkaTemplate.send(anyString(), anyString(), anyString())).thenReturn(future);
        userProducer.send("test");
        Mockito.verify(kafkaTemplate,Mockito.times(1)).send(anyString(), anyString(), anyString());
    }
}