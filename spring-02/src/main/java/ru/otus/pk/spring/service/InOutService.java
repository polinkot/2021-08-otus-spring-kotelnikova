package ru.otus.pk.spring.service;

public interface InOutService {

    void println(String line);

    String nextLine();   // а это же мне нужно - этот метод считывает строку через сканер

    int readInt();
}