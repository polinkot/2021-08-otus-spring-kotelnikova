package ru.otus.pk.spring.domain;

import lombok.Data;

@Data
public
class CorrectAnswers {
    private int count;

    public void increaseCount() {
        count++;
    }
}