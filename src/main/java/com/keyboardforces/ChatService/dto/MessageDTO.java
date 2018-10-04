package com.keyboardforces.ChatService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {
    @JsonProperty("message")
    private Object message;

    public MessageDTO() {
    }

    public MessageDTO(String message) {

        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
