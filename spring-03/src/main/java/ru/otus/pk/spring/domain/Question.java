package ru.otus.pk.spring.domain;

import lombok.Data;

import java.util.Collection;
import java.util.Optional;

@Data
public class Question {

    private final String value;

    private Collection<Answer> answers;

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
        enumerateAnswers();
    }

    private void enumerateAnswers() {
        int i = 1;
        for (Answer answer : this.answers) {
            answer.setNumber(i++);
        }
    }

    public Optional<Answer> getCorrectAnswer() {
        return answers.stream().filter(Answer::isCorrect).findFirst();
    }
}