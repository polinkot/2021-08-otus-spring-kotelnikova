package ru.otus.pk.spring.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class LibraryException extends RuntimeException {
    public LibraryException(String s) {
        super(s);
    }
}