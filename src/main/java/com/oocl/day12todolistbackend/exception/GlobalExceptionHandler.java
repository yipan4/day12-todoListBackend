package com.oocl.day12todolistbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoListEmptyTextException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleEmptyTextException(Exception e) {
        return e.getMessage();
    }
}
