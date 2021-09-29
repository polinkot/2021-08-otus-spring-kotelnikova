package ru.otus.pk.spring.domain;

import lombok.Data;

@Data
public
class QuizResult {
    private int count;
    private boolean passed;

    public void increaseCount() {
        this.count++;
    }
}