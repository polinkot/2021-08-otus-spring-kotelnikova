package ru.otus.pk.spring.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class AppException extends RuntimeException {
    public AppException(String s) {
        super(s);
    }
}