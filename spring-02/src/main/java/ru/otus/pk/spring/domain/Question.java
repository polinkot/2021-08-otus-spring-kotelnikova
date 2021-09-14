package ru.otus.pk.spring.domain;

import lombok.Data;
import ru.otus.pk.spring.exception.AppException;

import java.util.Collection;

import static java.lang.String.format;

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

    public String asString() {
        StringBuilder result = new StringBuilder(format("\n%s", value));
        answers.forEach(answer -> result.append(format("\n\t%d %s", answer.getNumber(), answer.getValue())));

        return result.toString();
    }

    public Answer getCorrectAnswer() {
        return answers.stream().filter(Answer::isCorrect).findFirst()
                .orElseThrow(() -> new AppException("No correct answer for question: " + this));
    }
}