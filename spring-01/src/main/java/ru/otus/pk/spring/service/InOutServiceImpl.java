package ru.otus.pk.spring.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;

@Getter
@RequiredArgsConstructor
public class InOutServiceImpl implements InOutService {

    private final PrintStream out;

    // TODO: 02.09.2021 input
}
