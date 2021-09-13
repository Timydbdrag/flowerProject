package com.greenstreet.warehouse.exception;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(Throwable cause) {
        super(cause);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
