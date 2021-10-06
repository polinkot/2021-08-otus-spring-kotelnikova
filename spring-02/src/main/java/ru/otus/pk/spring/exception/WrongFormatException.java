package ru.otus.pk.spring.exception;

public class WrongFormatException extends RuntimeException {
    public WrongFormatException(String s) {
        super(s);
    }
}