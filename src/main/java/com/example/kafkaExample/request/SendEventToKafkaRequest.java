package com.example.kafkaExample.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEventToKafkaRequest {
    private String name;
    private String surname;
}
