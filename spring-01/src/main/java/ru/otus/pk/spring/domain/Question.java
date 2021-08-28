package ru.otus.pk.spring.domain;

import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class Question {

    private final String value;

    private final List<Answer> answers;

    //вопрос - может лучше сделать другим отдельным методом?
    @Override
    public String toString() {
        return value + "\n\t"
                + answers.stream()//.filter(Objects::nonNull)
                .map(Answer::toString).collect(Collectors.joining("\n\t"));
    }
}
