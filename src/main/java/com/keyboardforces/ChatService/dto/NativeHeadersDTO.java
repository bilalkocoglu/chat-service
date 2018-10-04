package com.keyboardforces.ChatService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NativeHeadersDTO {
    @JsonProperty("destination")
    private Object destination;

    @JsonProperty("from")
    private Object from;

    @JsonProperty("content-length")
    private Object contentLength;

    public NativeHeadersDTO() {
    }

    public NativeHeadersDTO(Object destination, Object from, Object contentLength) {
        this.destination = destination;
        this.from = from;
        this.contentLength = contentLength;
    }

    public Object getDestination() {
        return destination;
    }

    public void setDestination(Object destination) {
        this.destination = destination;
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public Object getContentLength() {
        return contentLength;
    }

    public void setContentLength(Object contentLength) {
        this.contentLength = contentLength;
    }
}
