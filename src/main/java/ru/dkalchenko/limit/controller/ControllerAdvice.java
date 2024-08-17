package ru.dkalchenko.limit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dkalchenko.limit.dto.ErrorResponse;
import ru.dkalchenko.limit.exception.LimitSizeException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(LimitSizeException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse handleUserNotFoundException(LimitSizeException exception) {
        return new ErrorResponse(exception.getHttpStatus().value(), exception.getMessage());
    }
}
