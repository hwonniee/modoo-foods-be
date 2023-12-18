package com.modooFoods.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorWrapper> handleRuntimeException(final CustomException e) {
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorWrapper(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorWrapper> handleRuntimeException(final Exception e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorWrapper(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."));
    }
}