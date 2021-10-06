package ru.otus.pk.spring.service;

public interface LocalizedIOService {

    void println(String line);

    String readLine();

    int readInt(String prompt, String errMsg, int attemptsCount);

    String getMessage(String key);

    String getMessage(String key, Object... objects);

    void printlnLocalized(String key);

    void printlnLocalized(String key, Object... objects);

    int readIntLocalized(String prompt, String errMsg, int attemptsCount);
}