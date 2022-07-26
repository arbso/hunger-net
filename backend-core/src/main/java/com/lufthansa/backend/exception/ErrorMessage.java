package com.lufthansa.backend.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private int statusCode;
    private String message;
    private String description;

    public ErrorMessage(int statusCode, String message, String description) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }
}
