package ru.dkalchenko.limit.exception;

import org.springframework.http.HttpStatus;

public class LimitSizeException extends RuntimeException {

    private final HttpStatus httpStatus;

    public LimitSizeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
