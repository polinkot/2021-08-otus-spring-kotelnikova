package ru.otus.pk.spring.domain;

import lombok.Data;

@Data
public class Answer {
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
