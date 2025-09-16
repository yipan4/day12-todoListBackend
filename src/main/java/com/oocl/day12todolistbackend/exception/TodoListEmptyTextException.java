package com.oocl.day12todolistbackend.exception;

public class TodoListEmptyTextException extends RuntimeException {
    public TodoListEmptyTextException(String message) {
        super(message);
    }
}
