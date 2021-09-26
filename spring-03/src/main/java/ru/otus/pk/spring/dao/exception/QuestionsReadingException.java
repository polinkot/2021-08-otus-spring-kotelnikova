package ru.otus.pk.spring.dao.exception;

public class QuestionsReadingException extends RuntimeException {
    public QuestionsReadingException(Throwable e) {
        super(e);
    }
}