package ru.otus.pk.spring.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.io.PrintStream;

@Getter
@RequiredArgsConstructor
public class InOutServiceImpl implements InOutService {
    private final InputStream in;
    private final PrintStream out;
}