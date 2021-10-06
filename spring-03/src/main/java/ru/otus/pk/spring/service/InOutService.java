package ru.otus.pk.spring.service;

public interface InOutService {

    void println(String line);

    String readLine();

    int readInt(String prompt, String errMsg, int attemptsCount);
}