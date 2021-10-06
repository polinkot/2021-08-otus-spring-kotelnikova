package ru.otus.pk.spring.exception;

public class AppException extends RuntimeException {
    public AppException(String s) {
        super(s);
    }
}