package com.example.kafkaExample.controller;

import com.example.kafkaExample.request.SendEventToKafkaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaTemplate<String, SendEventToKafkaRequest> kafkaTemplate;

    @PostMapping(path = "/send")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String sendEventToKafka(@RequestBody final SendEventToKafkaRequest request) {
        kafkaTemplate.send("topic1", request);
        return "Message sent";
    }
}
