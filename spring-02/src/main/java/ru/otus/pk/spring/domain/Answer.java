package ru.otus.pk.spring.domain;

import lombok.Data;

@Data
public class Answer {
    private int number;
    private final String value;
    private final boolean correct;
}