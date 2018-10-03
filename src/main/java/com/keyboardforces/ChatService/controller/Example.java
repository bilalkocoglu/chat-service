package com.keyboardforces.ChatService.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Example.ENDPOINT)
public class Example {
    static final String ENDPOINT = "/example";

    @GetMapping(value = "deneme",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deneme(){
        return ResponseEntity.ok("başarılı");
    }
}
