package com.oocl.day12todolistbackend.exception;

public class TodoListMissingTextException extends RuntimeException {
    public TodoListMissingTextException(String message) {
        super(message);
    }
}
