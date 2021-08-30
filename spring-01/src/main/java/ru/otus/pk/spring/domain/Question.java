package ru.otus.pk.spring.domain;

import lombok.Data;

import java.util.Collection;

@Data
public class Question {

    private final String value;

    private final Collection<Answer> answers;
}