package com.greenstreet.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handler(ApiRequestException e) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiResponseException apiResponseException =
                new ApiResponseException(e.getMessage(), badRequest, LocalDateTime.now());

        return new ResponseEntity<>(apiResponseException, badRequest);
    }
}
