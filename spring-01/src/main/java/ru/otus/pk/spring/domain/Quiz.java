package ru.otus.pk.spring.domain;

import lombok.Data;

import java.util.Collection;

@Data
public class Quiz {
    private final Collection<Question> questions;

    //Вопрос - лучше сделать отдельный метод, возвращающий представление для печати?
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        questions.forEach(question -> {
            result.append(String.format("\n%s", question.getValue()));
            question.getAnswers().forEach(answer -> result.append(String.format("\n\t%s", answer.getValue())));
        });

        return result.toString();
    }
}