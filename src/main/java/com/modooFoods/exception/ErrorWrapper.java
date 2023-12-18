package com.modooFoods.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@RequiredArgsConstructor
public class ErrorWrapper {

    private LocalDateTime timeStamp = LocalDateTime.now();
    private HttpStatus httpStatus;
    private String messages;

    public ErrorWrapper(HttpStatus httpStatus, String messages) {
        this.httpStatus = httpStatus;
        this.messages = messages;
    }
}
