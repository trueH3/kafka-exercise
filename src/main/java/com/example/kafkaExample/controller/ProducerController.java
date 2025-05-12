package com.example.kafkaExample.controller;

import com.example.avro.User;
import com.example.kafkaExample.request.SendEventToKafkaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaTemplate<String, User> kafkaTemplate;

    @PostMapping(path = "/send")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String sendEventToKafka(@RequestBody final SendEventToKafkaRequest request) {
        final var user = User.newBuilder()
                .setName(request.getName())
                .setSurname(request.getSurname())
                .build();

        kafkaTemplate.send("topic1", user);
        return "Message sent";
    }
}
