package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsernameException(RuntimeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AccountCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAccountCreationException(RuntimeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UnauthorizedLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedLoginException(RuntimeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MessageCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMessageCreationException(RuntimeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UpdateMessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUpdateMessageException(RuntimeException e) {
        return e.getMessage();
    }
}
