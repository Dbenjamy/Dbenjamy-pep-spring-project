package com.example.exception;

public class AccountCreationException extends RuntimeException {
    public AccountCreationException() {
        super("Invalid username/password");
    }
}
