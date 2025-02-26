package com.example.exception;

public class MessageCreationException extends RuntimeException{
    public MessageCreationException() {
        super("Could not create message");
    }
}
