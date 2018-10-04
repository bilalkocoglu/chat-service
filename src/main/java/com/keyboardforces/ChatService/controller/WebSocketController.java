package com.keyboardforces.ChatService.controller;

import com.google.gson.Gson;
import com.keyboardforces.ChatService.dto.MessageDTO;
import com.keyboardforces.ChatService.dto.NativeHeadersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Gson gson = new Gson();

    @Autowired
    WebSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(@Payload String message, MessageHeaders messageHeaders){
        Date date = new Date();
        LinkedMultiValueMap linkedMultiValueMap = messageHeaders.get("nativeHeaders", LinkedMultiValueMap.class);
        System.out.println(linkedMultiValueMap.get("from").get(0));
        this.simpMessagingTemplate.convertAndSend("/chat",
                (date.getMonth()+1) + "." +date.getDay() + "-" + date.getHours() + ":" + date.getMinutes() + "-> " + message);
    }
}
