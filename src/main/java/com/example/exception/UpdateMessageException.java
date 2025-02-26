package com.example.exception;

public class UpdateMessageException extends RuntimeException {
    public UpdateMessageException() {
        super("Could not update message");
    }
}
