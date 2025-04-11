package com.workintech.spring17challenge.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErrorResponse {
    private int status;
    private String message;
    private Long timestamp;

    public ApiErrorResponse(int status, String message, Long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
