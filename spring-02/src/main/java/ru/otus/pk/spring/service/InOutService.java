package ru.otus.pk.spring.service;

import java.io.InputStream;
import java.io.PrintStream;

public interface InOutService {
    InputStream getIn();

    PrintStream getOut();
}