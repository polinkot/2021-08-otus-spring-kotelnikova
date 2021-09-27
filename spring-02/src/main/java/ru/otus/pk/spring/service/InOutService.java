package ru.otus.pk.spring.service;

public interface InOutService {

    void println(String line);

    void printf(String line, Object... args);

    String readLine();

    int readInt(String prompt, String errMsg, int attemptsCount);
}