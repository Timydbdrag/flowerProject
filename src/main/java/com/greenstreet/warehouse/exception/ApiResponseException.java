package com.greenstreet.warehouse.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApiResponseException {

    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime dateTime;

    public ApiResponseException(String message, HttpStatus httpStatus, LocalDateTime dateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponseException that = (ApiResponseException) o;
        return Objects.equals(message, that.message) && httpStatus == that.httpStatus && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, httpStatus, dateTime);
    }
}
