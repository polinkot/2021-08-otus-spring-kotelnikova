package ru.otus.pk.spring.dao.exception;

public class QuestionsReadingException extends RuntimeException {
    public QuestionsReadingException(String s, Exception e) {
        super(s, e);
    }
}