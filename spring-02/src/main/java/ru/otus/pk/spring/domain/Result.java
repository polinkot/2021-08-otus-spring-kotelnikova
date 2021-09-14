package ru.otus.pk.spring.domain;

import lombok.Data;

@Data
public class Result {
    private String firstName;
    private String lastName;
    private int correctAnswers;

    public void increaseCorrectAnswers() {
        correctAnswers++;
    }
}