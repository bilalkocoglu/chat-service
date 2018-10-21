package com.keyboardforces.ChatService.controller;

import com.google.gson.Gson;
import com.keyboardforces.ChatService.dto.ChatResponseDTO;
import com.keyboardforces.ChatService.dto.ConnectedNativeHeadersDTO;
import com.keyboardforces.ChatService.service.SessionManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class WebSocketController implements ApplicationListener<SessionConnectEvent>{
    private final SimpMessagingTemplate simpMessagingTemplate;

    static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);

    private Gson gson = new Gson();

    @Autowired
    WebSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Autowired
    SessionManagement sessionManagement;

    @MessageMapping(value = "/send/message")
    public void onReceivedMessage(@Payload String message, MessageHeaders messageHeaders){
        LocalDateTime now = LocalDateTime.now();

        now = now.plusHours(3);

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

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        LOGGER.info("Session Connect");
        LOGGER.info(event.toString());

        ConnectedNativeHeadersDTO connectedNativeHeadersDTO = gson.fromJson(event.getMessage().getHeaders().get("nativeHeaders").toString(),ConnectedNativeHeadersDTO.class);
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();

        //add online list
        sessionManagement.addSession(sessionId, connectedNativeHeadersDTO.getNickname().get(0));

        // get session id and nickname
        LOGGER.info(connectedNativeHeadersDTO.getNickname().get(0));
        LOGGER.info(sessionId);
    }

    @EventListener
    public void onSocketDisconnected(SessionDisconnectEvent event){
        LOGGER.info("Session Disconnect");

        String sessionId = event.getSessionId();

        // remove online list
        sessionManagement.removeSession(sessionId);

        // get session id
        LOGGER.info(event.toString());
        LOGGER.info(sessionId);

    }
}
