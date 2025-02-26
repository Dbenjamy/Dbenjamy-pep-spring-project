package com.example.exception;

public class UnauthorizedLoginException extends RuntimeException {
    public UnauthorizedLoginException() {
        super("Unauthorized login");
    }
}
