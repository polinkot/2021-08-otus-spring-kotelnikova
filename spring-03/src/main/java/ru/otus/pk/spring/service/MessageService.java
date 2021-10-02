package ru.otus.pk.spring.service;

public interface MessageService {
    String getMessage(String key);

    String getMessage(String key, Object[] objects);
}