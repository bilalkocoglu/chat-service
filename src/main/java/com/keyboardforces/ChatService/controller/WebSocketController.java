package com.keyboardforces.ChatService.controller;

import com.google.gson.Gson;
import com.keyboardforces.ChatService.dto.ChatResponseDTO;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Gson gson = new Gson();

    @Autowired
    WebSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping(value = "/send/message")
    public void onReceivedMessage(@Payload String message, MessageHeaders messageHeaders){
        Date date = new Date();

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");

        String nowString = now.format(formatter);

        LinkedMultiValueMap linkedMultiValueMap = messageHeaders.get("nativeHeaders", LinkedMultiValueMap.class);

        System.out.println(linkedMultiValueMap.get("from").get(0));

        ChatResponseDTO chatResponseDTO = new ChatResponseDTO();

        chatResponseDTO.setDate(nowString);
        chatResponseDTO.setMessage(message);
        chatResponseDTO.setInfo("kişi bilgisi");

        this.simpMessagingTemplate.convertAndSend("/chat", chatResponseDTO);
    }
}
