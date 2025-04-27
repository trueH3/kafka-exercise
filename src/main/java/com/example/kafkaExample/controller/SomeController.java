package com.example.kafkaExample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    @GetMapping(path = "/test")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public String sayHello() {
        return "Hello from app";
    }

}
